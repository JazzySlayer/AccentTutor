<%--
  Created by IntelliJ IDEA.
  User: anons
  Date: 8/28/16
  Time: 11:34 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <script>
        function EditDetails(id) {
            $.ajax({
                type:"POST",
                url:"<g:createLink controller="MFCCs" action="configure"/>",
                data:{wordId:id},
                success:function (data)
                {
                    console.log(data);
                    $('#templateName').val(data.configuration.templateName);
                    $('#standardPronunciation').val(data.configuration.standardPronunciation);
                    $("#editModal").modal("show");

                },
                error:function (err) {
                    console.log("Error")
                }

            });

        }
    </script>
</head>

<body>

<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title text-center">Configure</h4>
            </div>
            <div class="modal-body">
                <form enctype="multipart/form-data" id="selectFile" role="form" onsubmit="return false;">
                    <fieldset class="form">
                        <div class="form-group required">
                            <label for="templateName">Template File
                                <span class="required-indicator">*</span>
                            </label>
                            %{--<input type="file" name="templateName[]" id="templateName" multiple="" required>--}%
                            <input type="file" name="templateName" id="templateName" multiple="" required>
                        </div>
                        <div class="form-group required">
                            <label for="standardPronunciation">Standard Pronunciation File
                                <span class="required-indicator">*</span>
                            </label>
                            <input type="file" name="standardPronunciation" id="standardPronunciation" required>
                        </div>
                    </fieldset>
                    <button type="submit" class="btn btn-primary" onclick="sendToController1()">Configure</button>
                </form>
            </div>
            <div id="addWord"></div>
        </div>
    </div>
</div>


</body>
</html>