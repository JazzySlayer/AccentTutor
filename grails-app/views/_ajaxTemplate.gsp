<table class="table">
    <thead>
    <tr>
        <th>Result</th>
    </tr>
    </thead>
    <tbody>
        <g:if test="${result}">
            <g:each in="${result}" var="listValue" status="i">
                <tr>
                    <td>Template${i+1}</td>
                    <td>${listValue}</td>
                </tr>
            </g:each>
        </g:if>
        <g:else>
            <tr>
                <td>No Comparision</td>
            </tr>
        </g:else>
    </tbody>
</table>