var editor = null;
var vm = new Vue({
	el : '#rrapp',
	data : {
		showList : true,
		title : null,
		genTemp : {},
		allstyle : []
	},
	created : function() {
		$.ajax({
			url : rootPath + "/gen-style/select-list",
			type : "post",
			dataType : "json",
			success : function(data) {
				vm.allstyle = data;
				$.ajax({
					url : rootPath + "/gen-style/template",
					type : "post",
					dataType : "text",
					success : function(data) {
						
						$("#template").html( data);
					}

				})
			}

		})
	
	},
	methods : {
		query : function() {
			vm.reload();
		},
		add : function() {

			vm.showList = false;
			vm.title = "新增";
			vm.genTemp = {};
			if (editor != null) {
				$(".CodeMirror").remove()
			}
			setTimeout(function() {
				editor = CodeMirror.fromTextArea(document
						.getElementById("genContext"), {
					mode : "text/x-java", // 实现Java代码高亮
					lineNumbers : true, // 显示行号
					theme : "eclipse", // 设置主题
					lineWrapping : true, // 代码折叠
					foldGutter : true,
					gutters : [ "CodeMirror-linenumbers",
							"CodeMirror-foldgutter" ],
					matchBrackets : true, // 括号匹配
				// readOnly: true, //只读
				});
			}, 100)
		},
		update : function(event) {
			var id = getSelectedRow('genTemptable', 'id');
			
			if (id == null) {
				return;
			}

			vm.getInfo(id);
			vm.showList = false;
			vm.title = "修改";

		},
		saveOrUpdate : function(event) {
			var url = vm.genTemp.id == null ? "/gen-template/create"
					: "/gen-template/update";
			vm.genTemp.genContext = editor.getValue();
			console.log(vm.genTemp)
			$.ajax({
				type : "POST",
				url : rootPath + url,
				contentType : "application/json",
				data : JSON.stringify(vm.genTemp),
				success : function(r) {
					vm.reload();
					alert(r.msg);
				}
			});
		},
		del : function(event) {
			var ids = getSelectedRows('genTemptable', 'id');
			if (ids == null) {
				return;
			}
			layui.use('layer', function() {
                var layer = layui.layer;
                layer.confirm('确定要删除选中的记录？', function(index) {
                    layer.close(index);
					$.ajax({
						type : "POST",
						url : rootPath + "/gen-template/delete",
						data : {
							ids : ids
						},
						success : function(r) {
							alert(r.msg);
							vm.reload();
						}
					});
				});
			});
		},
		getInfo : function(id) {
			$.get(rootPath + "/gen-template/get?id=" + id, function(data) {
				vm.genTemp = data;
				if (editor != null) {
					$(".CodeMirror").remove()
				}
				var codetype = "text/x-java";
				var index = vm.genTemp.genFilename.indexOf('.java');
				if (index != -1) {
					codetype = "text/x-java";
				}
				index = vm.genTemp.genFilename.indexOf('.js');
				if (index != -1) {
					codetype = "javascript";
				}
				index = vm.genTemp.genFilename.indexOf('.html');
				if (index != -1) {
					codetype = "xml";
				}
				index = vm.genTemp.genFilename.indexOf('.xml');
				if (index != -1) {
					codetype = "xml";
				}
				index = vm.genTemp.genFilename.indexOf('.sql');
				if (index != -1) {
					codetype = "sql";
				}
				
				setTimeout(function() {
					editor = CodeMirror.fromTextArea(document
							.getElementById("genContext"), {
						mode : codetype, // 实现Java代码高亮
						lineNumbers : true, // 显示行号
						theme : "eclipse", // 设置主题
						lineWrapping : true, // 代码折叠
						foldGutter : true,
						gutters : [ "CodeMirror-linenumbers",
								"CodeMirror-foldgutter" ],
						matchBrackets : true, // 括号匹配
					// readOnly: true, //只读
					});
				}, 100)
			});
		},
		init : function() {
		},
		reload : function(event) {// 查询方法 网站发我
			// editor =
			// CodeMirror.fromTextArea(document.getElementById("genContext"), {
			// });
			vm.showList = true;
			layui.use('table', function() {
				var table = layui.table;
				table.render({
					elem : '#genTemptable' // 选定是那个DIV
					,
					url : rootPath + '/gen-template/page-list',
					cols : [ [ {
						type : 'checkbox'
					}, {
						field : 'id',
						title : 'ID',
						minWidth : 100,
						templet : function(data) {
							return data.id;
						}
					}, {
						field : 'description',
						title : '描述',
						minWidth : 100,
						templet : function(data) {
							return data.description;
						}
					}, {
						field : 'genFilename',
						title : '文件名称',
						minWidth : 100,
						templet : function(data) {
							return data.genFilename;
						}
					}, {
						field : 'state',
						title : '是否启用',
						minWidth : 100,
						templet : function(data) {
							if (data.state == 1) {
								return "启用";
							}
							if (data.state == 0) {
								return "停用";
							}
							return data.state;
						}
					} ] ],
					page : true, // 开启分页
					where : $("#searchForm").serializeJSON()
				});
			});
		}
	}
});
vm.reload();
$("#templateForm").submit(function(){
	vm.saveOrUpdate();
})