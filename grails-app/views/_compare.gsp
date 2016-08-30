<%--
  Created by IntelliJ IDEA.
  User: anons
  Date: 8/28/16
  Time: 11:34 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
    <style>
        .modal-content{
            margin-top: 150px;

        }
        .modal-header{
            background-color:#286090! important;
            color: #f6f1ed! important;
        }
        .modal-dialog{
            width: 30%;
        }

    </style>
    <title></title>
    <script>
        function sendToController() {
            var valueFName = document.getElementById('fileName').value;
            console.log("Whats the problem" + valueFName);
            if (valueFName) {
                var data = {
                    fileName: valueFName
                };
                $.ajax({
                    url: '${createLink(controller: 'MFCCs', action: 'index')}',
                    type: "POST",
                    data: data,
                    success: function (data) {
                        $("#compareModal").modal("hide");
                        if (data.messageType == "success") {
                            showNoty('success', 'Pronunciation Matched')
                        }
                        else {
                            showNoty('error', 'Pronunciation not Matched')
                        }
                    },
                    error: function (err) {
                        console.log("Error")
                    }
                });
            }
        }
    </script>
</head>

<body>

<div class="modal fade" id="compareModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title text-center">Configure</h4>
            </div>
            <div class="modal-body ">

                <fieldset class="form" >
                    <div class="form-group required" >
                        <label for="fileName">File Name
                            <span class="required-indicator">*</span>
                        </label>
                        <input type="file" name="fileName" id="fileName" required>
                    </div>
                </fieldset>
                <button class="btn btn-primary" onclick="sendToController()">Compare</button>
            </div>
            <div id="addWord"></div>
        </div>
    </div>
</div>


</body>
</html>