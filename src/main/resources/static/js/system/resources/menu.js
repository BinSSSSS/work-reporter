var setting = {
    data : {
        simpleData : {
            enable : true,
            idKey : "id",
            pIdKey : "parentid",
            rootPId : "0"
        },
        key : {
            url : "nourl"
        }
    }
};
var ztree;
var vm = new Vue({
    el : '#rrapp',
    data : {
        showList : true,
        title : null,
        menu : {
            parentname : null,
            parentid : "0",
            type : 1,
            level : 0
        }
    },
    methods : {
    	query : function(){
    		vm.reload();  
    	},
        getMenu : function() {
            // 加载菜单树
            $.get(rootPath + "/res/res-list", function(resList) {
                ztree = $.fn.zTree.init($("#menuTree"), setting, resList);
                var node = ztree.getNodeByParam("id", vm.menu.parentId);
                ztree.selectNode(node);
                try {
                    vm.menu.parentName = node.name;
                } catch (e) {
                }
            })
        },
        deleteparentid : function() {
            vm.menu.parentName = '空';
            vm.menu.parentId = "0";
        },
        add : function() {
            vm.showList = false;
            vm.title = "新增";
            vm.menu = {
                parentname : null,
                parentId : 0,
                type : 1,
                level : 0
            };
            vm.getMenu();
        },
        update : function() {
            var menuId = getMenuId();
            if (menuId == null) {
                return;
            }
            $.get(rootPath + "/res/get?id=" + menuId, function(res) {
                vm.showList = false;
                vm.title = "修改授权资源";
                vm.menu = res;
                vm.getMenu();
            });
        },
        del : function() {
            var menuId = getMenuId();
            if (menuId == null || menuId.length == 0) {
                return;
            }
            layui.use('layer', function() {

                var layer = layui.layer;
                layer.confirm('确定要删除选中的记录？', function (index) {
                    layer.close(index);
                    $.ajax({
                        type: "POST",
                        url: rootPath + "/res/delete",
                        data: {
                            resId: menuId
                        },
                        success: function (data) {
                            alert(data.msg);
                            if(data.success)
                               vm.reload();
                        }
                    });
                });
            });
        },
        saveOrUpdate : function(event) {
            // console.log("传递的资源信息: " + JSON.stringify(vm.menu));
            var url = vm.menu.id == null ? "/res/insert" : "/res/update";
            $.ajax({
                type : "POST",
                url : rootPath + url,
                contentType : "application/json",
                data : JSON.stringify(vm.menu),
                success : function(data) {
                    alert(data.msg);
                    vm.reload();
                    // if (r.code === 0) {
                    // alert('操作成功', function() {
                    // vm.reload();
                    // });
                    // } else {
                    // alert(r.msg);
                    // }
                }
            });
        },
        menuTree : function() {
            layui.use('layer', function() {
                var layer = layui.layer;
                layer.open({
                    type : 1,
                    offset : '50px',
                    skin : 'layui-layer-molv',
                    title : "选择菜单",
                    area : [
                        '300px', '450px' ],
                    shade : 0,
                    shadeClose : false,
                    content : jQuery("#menuLayer"),
                    btn : [
                        '确定', '取消' ],
                    btn1 : function(index) {
                        var node = ztree.getSelectedNodes();
                        // 选择上级菜单
                        vm.menu.parentId = node[0].id;
                        vm.menu.parentName = node[0].name;
                        layui.use('layer', function() {
                            var layer = layui.layer;
                            layer.close(index);
                        });
                    }
                });
            });
        },
        reload : function() {
            vm.showList = true;
            try{
//                Menu.table.refresh();
            	tableReflush();
            }catch(e){

            }
        }
    }
});
var Menu = {
    id : "menuTable",
    table : null,
    layerIndex : "0"
};
/**
 * 初始化表格的列
 */
Menu.initColumn = function() {
    var columns = [
        {
            field : 'selectItem',
            radio : true
        }, {
            title : '菜单ID',
            field : 'id',
            visible : false,
            align : 'center',
            valign : 'middle',
            width : '80px'
        }, {
            title : '菜单名称',
            field : 'name',
            align : 'center',
            valign : 'middle',
            sortable : true,
            width : '180px'
        },
        // {title: '上级菜单', field: 'parentid', align: 'center', valign:
        // 'middle',
        // sortable: true, width: '100px'},
        // {
        // title : '图标',
        // field : 'icon',
        // align : 'center',
        // valign : 'middle',
        // sortable : true,
        // width : '80px',
        // formatter : function(item, index) {
        // return item.icon == null ? '' : '<i class="' + item.icon + '
        // fa-lg"></i>';
        // }
        // },
        {
            title : '类型',
            field : 'type',
            align : 'center',
            valign : 'middle',
            sortable : true,
            width : '100px',
            formatter : function(item, index) {
                if (item.type == "0") {
                    return '<span class="label label-primary">目录</span>';
                }
                if (item.type == "1") {
                    return '<span class="label label-success">菜单</span>';
                }
                if (item.type == "2") {
                    return '<span class="label label-warning">按钮</span>';
                }
            }
        },
        // {
        // title : '排序号',
        // field : 'level',
        // align : 'center',
        // valign : 'middle',
        // sortable : true,
        // width : '100px'
        // },
        {
            title : '菜单URL',
            field : 'resUrl',
            align : 'center',
            valign : 'middle',
            sortable : true,
        }, {
            title : '授权标识',
            field : 'resKey',
            align : 'center',
            valign : 'middle',
            sortable : true
        } ]
    return columns;
};
function getMenuId() {
    var selected = $('#menuTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}
$(function() {
	tableReflush();
});

function tableReflush(){
	console.log("ResUrl: " + $("#resUrl").val())
	var colunms = Menu.initColumn();
    var table = new TreeTable(Menu.id, rootPath + "/res/res-list?resUrl=" + $("#resUrl").val(), colunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("parentid");
    table.setExpandAll(true);
    table.init();
    Menu.table = table;
}

$("#search").click(function(){
	vm.reload();
})
