<%--@elvariable id="word" type="com.example.dictionary.entity.Word"--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns:c="http://java.sun.com/jsp/jstl/core"
      xmlns:jsp="http://java.sun.com/JSP/Page">

<jsp:directive.page contentType="text/html;charset=UTF-8"/>


<div id="secForm"></div>

<c:forEach var="dictionary" items="${dictionaries.entrySet()}">
    <section class="ac-container" id="${dictionary.getKey()}s">
        <c:forEach var="word" items="${dictionary.getValue()}">

            <input id="${word.id}s" name="secondWidget" type="radio" checked/>
            <label for="${word.id}s">${word.value}</label>
            <article>
                <p>
                    <c:forEach var="value" items="${word.keys}">
                        "${value.value}"
                    </c:forEach>
                </p>
            </article>

        </c:forEach>
    </section>
</c:forEach>
<button onclick='createOrChange(false)'>edit/add</button>
<button type="button" class="button-class" onclick="remove(false)" value="delete">delete found word</button>

</html>