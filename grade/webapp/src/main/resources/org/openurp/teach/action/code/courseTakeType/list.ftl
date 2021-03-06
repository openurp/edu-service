[#ftl]
[@b.head/]
[@b.grid items=courseTakeTypes var="courseTakeType"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${courseTakeType.code}[/@]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${courseTakeType.id}"]${courseTakeType.name}[/@][/@]
    [@b.col width="15%" property="enName" title="英文名"]${courseTakeType.enName!}[/@]
    [@b.col width="20%" property="beginOn" title="生效时间"]${courseTakeType.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效时间"]${courseTakeType.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
