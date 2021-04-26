<%--@elvariable id="word" type="com.example.dictionary.entity.Word"--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:directive.page contentType="text/html;charset=UTF-8"/>




<c:forEach var="dictionary" items="${dictionaries.entrySet()}">
    <section class="ac-container" id="${dictionary.getKey()}s">
        <c:forEach var="word" items="${dictionary.getValue()}">

            <input title="${word.id}" value="${word.id}" id="${word.id}s" name="secondWidget" content="${word.id}" type="radio" checked/>
            <label for="${word.id}s">${word.value}</label>
            <article>
                <p>
                    <c:forEach var="value" items="${word.translations}">
                        "${value.value}"
                    </c:forEach>
                </p>
            </article>

        </c:forEach>
    </section>
</c:forEach>
<button onclick='createOrChange(false)'>edit/add</button>
<button type="button" class="button-class" onclick="remove(false)" value="delete">delete found word</button>

