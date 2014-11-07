package org.openurp.teach.domain

import org.beangle.data.model.bean.LongIdBean
import org.beangle.data.model.bean.TemporalOnBean
import org.openurp.teach.Lesson
import org.openurp.teach.CourseLimitItem
import org.openurp.teach.CourseLimitGroup

class CourseLimitGroupBean extends LongIdBean with CourseLimitGroup with TemporalOnBean {

  /** 教学任务 */
  var lesson: Lesson = _

  /** 条件列表 */
  var items: collection.mutable.Seq[CourseLimitItem] = _

  /** 最大人数 */
  var maxCount: Int = _

  /** 当前人数 */
  var curCount: Int = _

  /**授课对象还是选课对象*/
  var forClass: Boolean = true

}