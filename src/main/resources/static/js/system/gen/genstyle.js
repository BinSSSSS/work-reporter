var vm = new Vue({
	el : '#rrapp',
	data : {
		showList : true,
		title : null,
		genStyle : {}
	},
	methods : {
		copystyle : function() {
			var id = getSelectedRow('genStyleTable', 'id');
			if (id == null) {
				return;
			}
			$.ajax({
				url : "/gen-style/copy",
				type : "post",
				dataType : "json",
				data:{id:id},
				success : function(data) {
					alert(data.msg);
					vm.query()
				}
			})
		},
		query : function() {
			vm.reload();
		},
		add : function() {
			vm.showList = false;
			vm.title = "新增";
			vm.genStyle = {};
		},
		update : function(event) {
			var id = getSelectedRow('genStyleTable', 'id');
			if (id == null) {
				return;
			}
			vm.showList = false;
			vm.title = "修改";

			vm.getInfo(id)
		},
		saveOrUpdate : function(event) {
			var url = vm.genStyle.id == null ? "/gen-style/create"
					: "/gen-style/update";
			$.ajax({
				type : "POST",
				url : rootPath + url,
				contentType : "application/json",
				data : JSON.stringify(vm.genStyle),
				success : function(r) {
					vm.reload();
					alert(r.msg);

				}
			});
		},
		del : function(event) {
			var ids = getSelectedRows('genStyleTable', 'id');
			if (ids == null) {
				return;
			}
			layui.use('layer', function() {
                var layer = layui.layer;
                layer.confirm('确定要删除选中的记录？', function(index) {
                    layer.close(index);
					$.ajax({
						type : "POST",
						url : rootPath + "/gen-style/delete",
						data : {
							ids : ids
						},
						success : function(r) {
	
							alert(r.msg);
							vm.query();
						}
					});
                });
			});
		},
		getInfo : function(id) {
			$.get(rootPath + "/gen-style/get?id=" + id, function(genStyle) {
				vm.genStyle = genStyle;
			});
		},
		reload : function(event) {

			vm.showList = true;
			layui.use('table', function() {
				var table = layui.table;
				table.render({
					elem : '#genStyleTable' // 选定是那个DIV
					,
					url : rootPath + '/gen-style/page-list',
					cols : [ [ {
						type : 'checkbox'
					}, {
						field : 'id',
						title : '主键',
						minWidth : 100,
						templet : function(data) {

							return data.id;
						}
					}, {
						field : 'name',
						title : '框架名称',
						minWidth : 100,
						templet : function(data) {

							return data.name;
						}
					}, {
						field : 'description',
						title : '描述',
						minWidth : 100,
						templet : function(data) {

							return data.description;
						}
					} ] ],
					page : true, // 开启分页
					method: 'post',
					request : LayPagePostRequest,
					where : $("#searchForm").serializeJSON()
				});
			});
		}

	}
});

vm.reload();