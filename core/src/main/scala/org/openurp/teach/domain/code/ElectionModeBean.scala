package org.openurp.teach.domain.code

import org.openurp.platform.model.BaseCodeBean
import org.openurp.teach.code.ElectionMode
import org.openurp.teach.code.ElectionMode

class ElectionModeBean extends BaseCodeBean with ElectionMode{
    
	/**
	 * 指定
	 */
	var ASSIGEND :Integer=1

	/**
	 * 自选
	 */
	var SELF :Integer=2

}