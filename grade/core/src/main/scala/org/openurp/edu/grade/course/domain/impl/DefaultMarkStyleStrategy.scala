package org.openurp.edu.grade.course.domain.impl

import org.openurp.edu.base.code.model.ScoreMarkStyle
import org.openurp.edu.grade.course.model.GaGradeState
import org.openurp.edu.grade.course.domain.CourseGradeSettings
import org.openurp.edu.grade.course.model.ExamGradeState
import org.openurp.edu.grade.course.domain.MarkStyleStrategy
import org.openurp.edu.grade.code.model.GradeType
import org.beangle.data.dao.EntityDao
import org.openurp.edu.grade.course.model.CourseGradeState
import org.openurp.edu.grade.course.domain.CourseGradeSetting
import org.openurp.edu.grade.model.GradeState

/**
 * 默认成绩记录方式配置方法
 * @author chaostone
 *
 */
class DefaultMarkStyleStrategy extends MarkStyleStrategy {

  var entityDao: EntityDao = _

  var settings: CourseGradeSettings = _

  private def isDefault(style: ScoreMarkStyle): Boolean = {
    null == style || style.id == ScoreMarkStyle.Percent
  }

  def configMarkStyle(gs: CourseGradeState, gradeTypes: Seq[GradeType]) {
    val gradeState = gs.asInstanceOf[CourseGradeState]
    val setting = settings.getSetting(gradeState.lesson.project)
    if (isDefault(gradeState.markStyle))
      gradeState.markStyle = getDefaultCourseGradeMarkStyle(gradeState, setting)
    for (`type` <- gradeTypes) {
      val typeState = getState(gradeState, `type`)
      if (null == typeState.markStyle) {
        typeState.markStyle = getDefaultExamGradeMarkStyle(typeState, setting)
      }
    }
    entityDao.saveOrUpdate(gradeState)
  }

  /**
   * 查询缺省的总成绩记录方式
   *
   * @param state
   * @param setting
   * @return
   */
  protected def getDefaultCourseGradeMarkStyle(state: CourseGradeState, setting: CourseGradeSetting): ScoreMarkStyle = {
    var defaultMarkStyle = state.lesson.course.markStyle
    if (null == defaultMarkStyle) defaultMarkStyle = entityDao.get(classOf[ScoreMarkStyle], ScoreMarkStyle.Percent)
    defaultMarkStyle
  }

  /**
   * 查询缺省的考试成绩类型对应的记录方式
   *
   * @param typeState
   * @param setting
   * @return
   */
  protected def getDefaultExamGradeMarkStyle(state: GradeState, setting: CourseGradeSetting): ScoreMarkStyle = {
    if (state.gradeType.isGa) {
      state.asInstanceOf[GaGradeState].gradeState.markStyle
    } else {
      val typeState = state.asInstanceOf[ExamGradeState]
      if (typeState.gradeType.id == GradeType.Delay) {
        val endGradeState = typeState.gradeState.getState(new GradeType(GradeType.End))
        if (null == endGradeState) typeState.gradeState.markStyle else endGradeState.markStyle
      } else {
        entityDao.get(classOf[ScoreMarkStyle], ScoreMarkStyle.Percent)
      }
    }
  }

  private def getState(gradeState: CourseGradeState, gradeType: GradeType): GradeState = {
    var gradeTypeState = gradeState.getState(gradeType)
    if (null == gradeTypeState) {
      if (gradeType.isGa) {
        val gaState = new GaGradeState
        gaState.gradeType = gradeType
        gaState.gradeState = gradeState
        gradeState.gaStates += gaState
        gradeTypeState = gaState
      } else {
        val examState = new ExamGradeState
        examState.gradeType = gradeType
        examState.gradeState = gradeState
        gradeState.examStates += examState
        gradeTypeState = examState
      }
    }
    gradeTypeState
  }
}