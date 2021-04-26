<%--@elvariable id="word" type="org.hibernate.boot.jaxb.hbm.spi.JaxbHbmAnyValueMappingType"--%>
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
    function deleteTranslation(){
        const selected = document.getElementById("values").value;
        $.ajax({
            url: '${pageContext.request.contextPath}/dictionary/createOrChange/',
            method: 'delete',
            data:{
                id: ${word.id==null ? "null" : word.id},
                wordId: selected
            },
            success: function(){
                location.reload()
            }
        });
    }
    function addTranslation(){
        const optionElement = document.createElement('option');
        // optionElement.id="0";
        optionElement.value="";
        optionElement.textContent=document.getElementById("valueValue").value;
        document.getElementById("values").appendChild(optionElement);
    }

    function editOrCreateWord(){
        let word = {};
        word.translations=[];
        word.id=${word.id==null ? "null" : word.id};
        word.value=document.getElementById("wordValue").value;
        word.type=document.getElementById("wordType").value;
        document.getElementById("values").childNodes.forEach(function(key) {
            if(key.nodeName==="OPTION"&&key.value===""){
            word.translations.push({id:key.value,value:key.textContent})}
        })
            if(word.translations.length>0){
        $.ajax({
            url: '${pageContext.request.contextPath}/dictionary/createOrChange/',
            method: 'post',
            contentType: 'application/json',
            data:JSON.stringify(word),
            success: function(data){
                window.location.href='${pageContext.request.contextPath}/dictionary/createOrChange/?id='+data;
            }
        });
        }
    }
</script>
<a>key</a>
<input id="wordValue" type="text" size="40" value="${word.value}">
<c:if test="${word.id==null}">
<select id="wordType">
    <option value="LATIN4" >LATIN4</option>
    <option value="ARAB5" >ARAB5</option>
</select>
</c:if>
<c:if test="${word.id!=null}">
    <select id="wordType">
        <option value="${word.type}" >${word.type}</option>
    </select>
</c:if>
<a>values</a>
<select id="values">
<c:forEach var="translation" items="${word.translations}">
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
