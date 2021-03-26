<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns:c="http://java.sun.com/jsp/jstl/core"
      xmlns:jsp="http://java.sun.com/JSP/Page">
<jsp:directive.page contentType="text/html;charset=UTF-8" />
<jsp:useBean id="words" scope="request" type="com.example.dictionary.Entity.Words"/>
<html>
<body>
<script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript" ></script>
<script>
    function remove(){
        var selected =document.getElementById("values").value
        $.ajax({
            url: '/dictionary_war_exploded/dictionary/createOrChange/' + selected,
            method: 'delete',
            contentType: 'application/json',
            data:JSON.stringify(
                ${words.id==null ? "" : words.id}
            ),
            success: function(data){
                location.reload()
            }
        });
    }
    function add(){
        var optionElement=document.createElement('option');
        optionElement.id="0";
        optionElement.value=document.getElementById("valueValue").value;
        optionElement.textContent=document.getElementById("valueValue").value;
        document.getElementById("values").appendChild(optionElement);
    }

    function post(){
        var keys = "[";
        document.getElementById("values").childNodes.forEach(function(key) {
            if (key.id=="0"){
                if (keys.length>1)
                keys+=","
            keys+=
                JSON.stringify({"id": null,
                    "value":key.textContent,
                    "words": [{"value": ""}]})
            }
        })
        keys+="]"
        $.ajax({
            url: '/dictionary_war_exploded/dictionary/createOrChange/',
            method: 'post',
            contentType: 'application/json',
            data:JSON.stringify(
        {
                "id": ${words.id==null ? null : words.id},
                "value": document.getElementById("wordValue").value,
                "type": document.getElementById("wordType").value,
                "keys": JSON.parse(keys)
            }),
            success: function(data){
                document.location.href = "http://localhost:8080/dictionary_war_exploded/dictionary/createOrChange/"+data;
            }
        });
    }
</script>
<a>key</a>
<input id="wordValue" type="text" size="40" value="${words.value}">
<input id="wordType" type="text" size="40" value="${words.type}">
<a>values</a>
<select id="values">
<c:forEach var="word" items="${words.keys}">
            <option value="${word.getId()}">${word.getValue()}</option>
</c:forEach>
</select>
<input type="button" class="button-class" onclick="remove()" value="delete">
<a>new value</a>
<input id="valueValue" type="text" size="40"/>
<input type="button" class="button-class" onclick='add()' value="add value">
<input type="button" class="button-class" onclick="post()" value="add/edit">
</body>
</html>
