package org.openurp.teach.action.code

import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.data.model.annotation.code
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.teach.code.StdLabel
import org.openurp.teach.code.StdLabelType
import org.openurp.teach.code.StdType

class StdLabelAction extends RestfulAction[StdLabel] {
  	override def editSetting(entity: StdLabel) = {
		val query = OqlBuilder.from(classOf[StdLabelType] )
		query.orderBy("name")
		val labelTypes = entityDao.search(query)
		put("labelTypes", labelTypes)
		super.editSetting(entity)
	}
}

class StdLabelTypeAction extends RestfulAction[StdLabelType]

class StdTypeAction extends RestfulAction[StdType] {
  	override def editSetting(entity: StdType) = {
		val query = OqlBuilder.from(classOf[StdLabelType] )
		query.orderBy("name")
		val labelTypes = entityDao.search(query)
		put("labelTypes", labelTypes)
		super.editSetting(entity)
	}
}