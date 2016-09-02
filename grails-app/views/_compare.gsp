<%--
  Created by IntelliJ IDEA.
  User: anons
  Date: 8/28/16
  Time: 11:34 AM
--%>
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

    </script>

<div class="modal fade" id="compareModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title text-center">Configure</h4>
            </div>
            <div class="modal-body ">
                <form enctype="multipart/form-data" id="selectFile" role="form" onsubmit="return false;">
                    <fieldset class="form" >
                        <div class="form-group required" >
                            <label for="fileName">File Name
                                <span class="required-indicator">*</span>
                            </label>
                            <input type="file" name="fileName" id="fileName" required>
                        </div>
                    </fieldset>
                    <button class="btn btn-primary" onclick="sendToController()">Compare</button>
                </form>
            </div>
            <div id="addWord"></div>
        </div>
    </div>
</div>