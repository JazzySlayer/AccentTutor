<%--
  Created by IntelliJ IDEA.
  User: anijor
  Date: 6/27/2016
  Time: 9:55 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <title></title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'home.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.css')}" type="text/css">
    %{----}%
    %{--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">--}%

    <script src="js/audiodisplay.js"></script>
    <script src="js/recorder.js"></script>
    <script src="js/main.js"></script>
     <script src="js/jquery.js"></script>
    <script src="js/soundmanager2.js"></script>
    <asset:javascript src="noty/packaged/jquery.noty.packaged.min.js"/>
    <script src="js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function(){
            $("#result").hide();
            $("#editModal").modal("hide");
            $("#compareModal").modal("hide");
            getStandardPronunciation();
        });
        function getStandardPronunciation(){
            $.ajax({
                url: '${createLink(controller: 'MFCCs', action: 'initiate')}',
                type: "POST",
                success: function (data) {
                    console.log("sucess");
                    if (data.messageType == "fail") {
                        showNoty('error', 'System Not configured. Ask admin to configure the system.');
//                        $('#NamastePronunciation').on('click', showNoty('error', 'Not configured. Ask admin to configure the system.'));
//                        $('#DhanyabaadPronunciation').on('click', showNoty('error', 'Not configured. Ask admin to configure the system.'));
                    }
                    else if (data.messageType == "success"){
                        $('#NamastePronunciation').each(function(){
                            $(this).wrapInner('<a href="templates/' + data.pronun1 + '" />');
                        });
                        $('#DhanyabaadPronunciation').each(function(){
                            $(this).wrapInner('<a href="templates/' + data.pronun2 + '" />');
                        });
                    }

                },
                error: function (err) {
                    console.log("Error")
                }
            });
        }

        function showCompareModal(wordId){
            $("#compareModal").modal("show");
            $('#wordId').val(wordId);
        }

        soundManager.setup({
            url: 'templates/',
            flashVersion: 9, // optional: shiny features (default = 8)
            // optional: ignore Flash where possible, use 100% HTML5 mode
            // preferFlash: false,
            onready: function() {
                // Ready to use; soundManager.createSound() etc. can now be called.
            }
        });

        function sendToController() {
//            var valueFName = document.getElementById('fileName').value;
            var valueFName = $('#fileName').val();
            var test = valueFName.split(".");
            if(test[test.length-1]!="wav"){
                showNoty('error', 'Sound file must be .wav format.');
            }
            else {
                var fd = new FormData();
                fd.append('valueFName',$('#fileName')[0].files[0]);
                fd.append('wordId',$('#wordId').val());


                if (valueFName) {
                    $.ajax({
                        url: '${createLink(controller: 'MFCCs', action: 'index')}',
                        type: "POST",
                        data: fd,
                        contentType: false,
                        processData: false,
                        success: function (data) {
                            console.log("sucess");
                            $("#compareModal").modal("hide");
                            $('#fileName').val('');
                            if (data.messageType == "success") {
                                showNoty('success', 'Pronunciation Matched');

                            }
                            else {
                                showNoty('error', 'Pronunciation not Matched');
                            }
                            $("#result").show();
                            $("#ajaxAfter").html("<table><tr><td>Distance with Template 1: &nbsp;</td><td>"
                                    +data.result[0]+"</td></tr>"+"<tr><td>Distance with Template 2: &nbsp;</td><td>"
                            +data.result[1]+"</td></tr>"+"<tr><td>Distance with Template 3: &nbsp;</td><td>"
                                    +data.result[2]+"</td></tr>"+"<tr><td>Distance with Template 4: &nbsp;</td><td>"
                                    +data.result[3]+"</td></tr>"+"</table>")
                        },
                        error: function (err) {
                            console.log("Error")
                        }
                    });
                }
            }

        }

        function sendToController1() {
            var templateName = document.getElementById('templateName');
            var standardPronunciation = $('#standardPronunciation').val();
            if(templateName&&standardPronunciation){
                var files = templateName.files;

                if(files.length!=4){
                    showNoty('error', 'Please select 4 pronunciation template.');
                }
                else{
                    var proceed = true;
                    for(var i=0;i<4;i++){
                        var name = files[i].name.split(".");
                        if(name[name.length-1] != "wav"){
                            showNoty('error', 'Sound file must be .wav format.');
                            proceed = false;
                            break;
                        }
                    }
                    var test = standardPronunciation.split(".");
                    if((test[test.length-1]!="wav")){
                        showNoty('error', 'Sound file must be .wav format.');
                        proceed = false;
                    }
                    if(proceed){
                        var fd = new FormData();
                        fd.append('templateName',$('#templateName')[0].files[0]);
                        fd.append('templateName1',$('#templateName')[0].files[1]);
                        fd.append('templateName2',$('#templateName')[0].files[2]);
                        fd.append('templateName3',$('#templateName')[0].files[3]);
                        fd.append('standardPronunciation',$('#standardPronunciation')[0].files[0]);
                        fd.append('wordId',$('#wordId').val());

                        $.ajax({
                            url: '${createLink(controller: 'MFCCs', action: 'saveConfigure')}',
                            type: "POST",
                            data: fd,
                            contentType: false,
                            processData: false,
                            success: function (data) {
                                if (data.messageType == "SaveSuccess") {
                                    $("#editModal").modal("hide");
                                    $('#templateName').val('');
                                    $('#standardPronunciation').val('');
                                    $('#wordId').val('');
                                    showNoty('success', 'Configuration Saved')
                                }
                                else {
                                    showNoty('error', 'Error in Saving Configuration')
                                }
                            },
                            error: function (err) {
                                showNoty('error', 'Error in Saving Configuration')                            }
                        });

                    }
                }

            }
        }

        function showNoty(type,message){
            var n = noty({
                layout: 'top',
                theme: 'relax',
                type: type,
                text: message,
                animation: {
                    open: {height: 'toggle'},
                    close: {height: 'toggle'},
                    easing: 'swing', // easing
                    speed: 500
                },
                timeout: 4000
            })
        }
        function showLongNoty(type,message){
            var n = noty({
                layout: 'topCenter',
                theme: 'relax',
                type: type,
                text: message,
                animation: {
                    open: {height: 'toggle'},
                    close: {height: 'toggle'},
                    easing: 'swing', // easing
                    speed: 500
                },
                timeout: 30000
            })
        }
        function showWarningNoty() {
            $('#editModal').addClass("inactiveLink");
            var n =noty({
                layout: 'topCenter',
                theme: 'relax',
                text: "Template and standard pronunciation for the selected word is already configured. " +
                "If you want to change it then click \'Ok\' else click \'Cancel\'",
                animation: {
                    open: {height: 'toggle'},
                    close: {height: 'toggle'},
                    easing: 'swing', // easing
                    speed: 500
                },
                buttons: [
                    {addClass: 'btn btn-primary', text: 'Ok', onClick: function($noty) {
                        $noty.close();
                        $('#editModal').removeClass("inactiveLink");
                        showLongNoty('information', 'Select 4 templates and a standard pronunciation file for the selected Word. ' +
                                'All the file must be of .wav format. Template files must have same name ' +
                                'followed by increasing integer number as suffix which starts from 1. ' +
                                'Example for word Namaste: First Template: Namaste1.wav, Second Template: Namaste2.wav. Third Template: ' +
                                'Namaste3.wav and Fourth Template: Namaste4.wav')
                    }
                    },
                    {addClass: 'btn btn-danger', text: 'Cancel', onClick: function($noty) {
                        $noty.close();
//                        $('#editModal').removeClass("inactiveLink");
                        $("#editModal").modal("hide");
                    }
                    }
                ],
                timeout: 1000
            })
        }

    </script>
    <style>
        .logout{
            margin-left: 1265px;
        }
        .result{
            width: 50% !important;
        }
    </style>

</head>

<body>
<div class="jumbotron head">
    <h1 class="text-center">Accent Tutor</h1>
    <sec:ifAllGranted roles="ROLE_ADMIN">
        <g:if test="${sec.loggedInUserInfo(field: 'username')}">
            <g:link controller="logout" action="index"><button type="button" class="btn btn-default logout"> Logout</button></g:link>
        </g:if>
        <g:else>
            <button type="button" class="btn btn-default logout"><g:link controller="login" action="index"> Login</g:link></button>
            <g:link controller="login"  action="index"><button type="button" class="btn btn-default logout"> Login</button></g:link>
        </g:else>
    </sec:ifAllGranted>
</div>
<div class="container-fluid">
    <!-- Modal Button-->


    <!-- Modal -->
    <div id="myModalResult" class="modal fade" role="dialog">
        <div class="modal-dialog result">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Result</h4>
                </div>
                <div class="modal-body" id="ajaxAfter">
                    <g:render template="/ajaxTemplate"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>

    %{--<div class="row">
        <div class="col-md-12 nav-wrapper">
            <div id="navbar">
                <ul class="nav navbar-nav navbar-centre">
                    <sec:ifAllGranted roles="ROLE_ADMIN">
                        <li><g:link controller="MFCCs" action="configure"> Setting</g:link></li>
                    </sec:ifAllGranted>
                    --}%%{--<li><g:loginControl /></li>--}%%{--
                </ul>
            </div>
        </div>
    </div>--}%
    <g:render template="configure"/>
    <g:render template="compare"/>
    %{--<input type="hidden" id="fileName" value="">--}%
    <div class="row">
        <div class="col-md-2">
        </div>
        <div class="col-md-8">
            <table class="table table-responsive">
                <tbody>
                <tr>
                    <td><b>Namaste</b></td>
                    <td>
                        <div id="NamastePronunciation">
                            <button type="button" class="btn btn-default btn1 btn-danger">
                                <span class="glyphicon glyphicon-play-circle"></span>  Play
                            </button>
                        </div>
                        %{--<a href="mediaOfSounds/NN2.wav">

                        </a>--}%
                    </td>
                    <td>
                        <button type="button" class="btn btn-default btn1 btn-primary" onclick="showCompareModal(1);">
                            <span class="glyphicon glyphicon-align-justify"></span>  Compare
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-default btn1 btn-success" data-toggle="modal" data-target="#myModal" onclick='changeName("NN",false);'>
                            <span class="glyphicon glyphicon-record "></span>  Record
                        </button>
                    </td>
                    <sec:ifAllGranted roles="ROLE_ADMIN">
                        <td>
                            <button type="button" class="btn btn-default btn1 btn-warning" data-toggle="modal" onclick="EditDetails('1')"  >
                                <span class="glyphicon glyphicon-edit"></span>  Configure
                            </button>
                        </td>
                    </sec:ifAllGranted>

                    %{--<sec:ifAllGranted roles="ROLE_ADMIN">--}%
                        %{--<td><button type="button" class="btn btn-default btn1" onclick="EditDetails('1')" ><span class="glyphicon glyphicon-edit fa-5x"> Edit</span></button></td>--}%
                    %{--</sec:ifAllGranted>--}%
                </tr>
                <tr>
                    <td><b>Dhanyabaad</b></td>
                    <td>
                        <div id="DhanyabaadPronunciation">
                            <button type="button" class="btn btn-default btn1 btn-danger">
                                <span class="glyphicon glyphicon-play-circle"></span>  Play
                            </button>
                        </div>
                        %{--<a href="templates/dd3.wav">

                        </a>--}%
                    </td>
                    <td>
                        <button type="button" class="btn btn-default btn1 btn-primary" onclick="showCompareModal(2);">
                            <span class="glyphicon glyphicon-align-justify"></span>  Compare
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-default btn1 btn-success" data-toggle="modal" data-target="#myModal" onclick='changeName("dd",false)'>
                            <span class="glyphicon glyphicon-record"></span>  Record
                        </button>
                    </td>
                    <sec:ifAllGranted roles="ROLE_ADMIN">
                    <td>
                        <button type="button" class="btn btn-default btn1 btn-warning" data-toggle="modal" onclick="EditDetails('2')"  >
                            <span class="glyphicon glyphicon-edit"></span>  Congifure
                        </button>
                    </td>
                    </sec:ifAllGranted>
                        %{--<td>--}%
                    %{--<sec:ifAllGranted roles="ROLE_ADMIN">--}%
                            %{--<button type="button" class="btn btn-default btn1" onclick="EditDetails('2')" ><span class="glyphicon glyphicon-edit "> Edit</span></button>--}%
                    %{--</sec:ifAllGranted>--}%
                        %{--</td>--}%

                    </tr>
                </tbody>
            </table>

                <button type="button" id="result" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModalResult">View Report</button>
        </div>
        <div class="col-md-2">


        </div>



    </div>

    <div class="row">


        <!-- Modal -->
        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Record</h4>
                    </div>

                        <div id="viz" class="modal-body">
                            <canvas id="analyser"></canvas><input type="button" value="Start & Stop" class="button" onclick="toggleRecording(this);">
                            <canvas id="wavedisplay"></canvas>  <a id="save" href="#"><input type="button" class="button" value="Export" ></a>
                        </div>
                    </div>
                    <div class="modal-footer">

                    </div>
                </div>

            </div>
        </div>

    </div>



</div>
</body>
</html>