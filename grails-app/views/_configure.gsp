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
                    $("#editModal").modal("show");
                    var a = data.configuration.id;
//                    alert("id="+a);
//                    $('input:hidden#wordId').val(a);
                    $('#wordId').val(a);
//                    alert($("#wordId").val());
                    if(data.configuration.templateName!=null){
                        showWarningNoty();
                    }
                    else {
                        showLongNoty('information', 'Select 4 templates and a standard pronunciation file for the selected Word. ' +
                                'All the file must be of .wav format. Template files must have same name ' +
                                'followed by increasing integer number as suffix which starts from 1. ' +
                                'Example for word Namaste: First Template: Namaste1.wav, Second Template: Namaste2.wav. Third Template: ' +
                                'Namaste3.wav and Fourth Template: Namaste4.wav');
                    }
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
                        <input id="wordId" type="hidden"/>
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