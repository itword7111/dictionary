<%--@elvariable id="translation" type="com.example.dictionary.entity.Translation"--%>
<%--<input id="wordType" type="text" size="40" value="${word.type}">--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:directive.page contentType="text/html;charset=UTF-8" />
<jsp:directive.page import="com.example.dictionary.model.TypeOfDictionary" />
<html>
<body>
<script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript" ></script>
<script>
    const href = '${pageContext.request.scheme}' + '://' + '${pageContext.request.serverName}' + ':' + '${pageContext.request.serverPort}' + '/' + '${pageContext.request.contextPath}' + '/dictionary/createOrChange/';

    class Word{
    }
    class Translation{
        constructor(id, value) {
            this.id = id;
            this.value=value;
        }
    }
    function deleteTranslation(){
        const selected = document.getElementById("values").value;
        $.ajax({
            url: '${pageContext.request.contextPath}/dictionary/createOrChange/' + selected,
            method: 'delete',
            contentType: 'application/json',
            data:JSON.stringify(
                ${word.id==null ? "" : word.id}
            ),
            success: function(){
                location.reload()
            }
        });
    }
    function addTranslation(){
        const optionElement = document.createElement('option');
        optionElement.id="0";
        optionElement.value=document.getElementById("valueValue").value;
        optionElement.textContent=document.getElementById("valueValue").value;
        document.getElementById("values").appendChild(optionElement);
    }

    function editOrCreateWord(){
        let word = new Word();
        word.keys=[];
        word.id=${word.id==null ? null : word.id};
        word.value=document.getElementById("wordValue").value;
        word.type=document.getElementById("wordType").value;
        if(word.value!==""){
        document.getElementById("values").childNodes.forEach(function(key) {
            if(key.nodeName==="OPTION"){
            word.keys.push(new Translation(null,key.textContent))}
        })
        $.ajax({
            url: '${pageContext.request.contextPath}/dictionary/createOrChange/',
            method: 'post',
            contentType: 'application/json',
            data:JSON.stringify(word),
            success: function(data){
                document.location.href = href+data;
            }
        });
        }
    }
</script>
<a>key</a>
<input id="wordValue" type="text" size="40" value="${word.value}">
<c:if test="${word.id==0}">
<select id="wordType">
    <option value="latin4" >${TypeOfDictionary.latin4.toString()}</option>
    <option value="arab5" >${TypeOfDictionary.arab5.toString()}</option>
</select>
</c:if>
<c:if test="${word.id!=0}">
    <select id="wordType">
        <option value="${word.type}" >${word.type}</option>
    </select>
</c:if>
<a>values</a>
<select id="values">
<c:forEach var="translation" items="${word.keys}">
            <option value="${translation.id}">${translation.value}</option>
</c:forEach>
</select>
<input type="button" class="button-class" onclick="deleteTranslation()" value="delete">
<a>new value</a>
<input id="valueValue" type="text" size="40"/>
<input type="button" class="button-class" onclick="addTranslation()" value="add value">
<input type="button" class="button-class" onclick="editOrCreateWord()" value="add/edit">
</body>
</html>
