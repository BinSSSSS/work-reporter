var $vue = Vue.directive('select2', {
    inserted: function(el, binding, vnode) {
        let options = binding.value || {};

        $(el).select2(options).on("select2:select", (e)=>{
            // v-model looks for
            // - an event named "change"
            // - a value with property path "$event.target.value"
            el.dispatchEvent(new Event('change', {
                target: e.target
            })); // 说好的双向绑定，竟然不安套路
//            console.log("插入操作");
        });

    },
    update: function(el, binding, vnode) {
        for (var i = 0; i < vnode.data.directives.length; i++) {
            if (vnode.data.directives[i].name == "model") {
                $(el).val(vnode.data.directives[i].value);
//                console.log("更新的新值:" + vnode.data.directives[i].value);
            }
        }
        $(el).trigger("change");
//        console.log("更新操作");
        $(".select2-selection__choice__remove").click(function(){

            console.log("数据被移除");
            for (var i = 0; i < vnode.data.directives.length; i++) {
                if (vnode.data.directives[i].name == "model") {
                    vnode.data.directives[i].value = $("select.select2")[0].value;
                    $(el).val($("select.select2")[0].value);
                    var roleIds = vnode.data.directives[i].value;
                    vm.user.roleIds = roleIds.split(",");
                }
            }
            $(el).trigger("change");
//            console.log(vnode);
            $vue.update(el,binding,vnode);
        });
//        console.log(vnode);
    }
});
var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        user: {
            roleIds: []
        },
        roleoptions: null,
// user_roles: null
    },

    methods: {
        // 查询方法
        query: function() {
            vm.reload();
        },
        // 编辑方法
        toupdate: function() {
            var userid = getSelectedRow('usertable', 'id');
            if (userid == null || userid.length == 0) {
                return;
            }

            vm.showList = false;
            vm.title = "编辑用户";
            vm.user = {
                roleIds: []
            };
            // 去拿到当前编辑用户的信息
            vm.getUser(userid);
        },
        update: function() {
            console.log(JSON.stringify(vm.user));
            $.ajax({
                type: "POST",
                url: rootPath + "/user/update",
                contentType: "application/json",
                data:JSON.stringify(vm.user),
                success: function(data) {
                    alert(data.msg);
                    vm.reload();
                }
            });
        },
        del: function(event) {
            var userIds = getSelectedRows('usertable', 'id');
            
            if (userIds == null || userIds.length == 0) {
                return;
            }
            
            var cbox =  getSelectedRows('usertable', 'id');
            layer.confirm('确定要删除选中的记录？',function(index){
                layer.close(index);
                $.ajax({
                    type: "POST",
                    url: rootPath + '/user/delete',
                    dataType: "json",
                    data: {
                        ids: userIds
                    },
                    success: function(r) {
                        if (r.success) {
                            alert(r.msg);
                            vm.reload();
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getUser: function(userid) {

            $.get(rootPath + '/role/all-role',
                function(roleList) {
                    vm.roleoptions = roleList;
                    $.get(rootPath + '/user/get?id=' + userid,
                        function(user) {
                            vm.user = user;
                        });
                });
        },
        reload: function() {
            vm.showList = true;
//            console.log(LayPagePostRequest);
            layui.use('table',
                function() {
                    var table = layui.table;
                    table.render({
                        elem: '#usertable', // 选定是那个DIV
                        url: '/user/user-list',
                        method: 'post',
                        page: true ,// 开启分页
                        cols: [[
                            {
                                type: "checkbox"
                            },
                            {
                                field: 'id',
                                width: 180,
                                title: 'ID'
                            },
                            {
                                field: 'username',
                                width: 180,
                                title: '用户名'
                            },
                            {
                                field: 'phone',
                                width: 180,
                                title: '手机号'
                            },
                            {
                                field: 'email',
                                width: 180,
                                title: '邮箱'
                            },
                            {
                                field: 'state',
                                title: '账号状态',
                                minWidth: 100,
                                templet: function(data) {
                                    if (data.state == "OK") {
                                        return "正常";
                                    }
                                    if (data.state == "lock") {
                                        return "<font color='red'>封锁</font>";
                                    }
                                    if (data.state == "die") {
                                        return "未激活";
                                    }
                                    return "数据异常";
                                }
                            }
                        ]],
                        request: LayPagePostRequest,
                        // 查询条件
                        where:$("#searchForm").serializeJSON()
                    });
                });
        },
        download: function() {
            location.href = "/user/export?" + $("#searchForm").serialize();
        }
    }
});

vm.reload();
$('.select2').select2();