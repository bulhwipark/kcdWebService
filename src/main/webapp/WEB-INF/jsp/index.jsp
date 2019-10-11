<%--
  Created by IntelliJ IDEA.
  User: saltlux
  Date: 2019-10-10
  Time: 오후 1:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>jsp index</h2>
</body>
<script src="/static/lib/jQuery-3.4.1.min.js"/>
<script type="text/javascript">
    $(function(){
       $.ajax({
           url:"/selectTemp",
           type:'get',
           data:{},
           dataType:'json',
           success:function(data){
               console.log(data);
           }
       })
    });
</script>
</html>
