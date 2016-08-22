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
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}" type="text/css">
    <script src="js/audiodisplay.js"></script>
    <script src="js/recorder.js"></script>
    <script src="js/main.js"></script>
     <script src="js/jquery.js"></script>
    <script src="js/soundmanager2.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script>
        var ownfilename="myrecording00";
        function changeName(value){
            value+="_record"
            $("#fileName").val(value);
            console.log("-----------------"+$("#fileName").val())
        }
        soundManager.setup({
            url: 'mediaOfSounds/',
            flashVersion: 9, // optional: shiny features (default = 8)
            // optional: ignore Flash where possible, use 100% HTML5 mode
            // preferFlash: false,
            onready: function() {
                // Ready to use; soundManager.createSound() etc. can now be called.
            }
        });
        function sendToController(value){
            if(value){
                var data = {
                    fileName:value
                }
                $.ajax({
                    url:'${createLink(controller: 'MFCC', action: 'index')}',
                    type:"POST",
                    data:data,
                    success:function(data){
                        if(data.messageType=="success"){
                            var n = noty({
                                layout: 'top',
                                theme: 'relax',
                                type: 'success',
                                text: 'Match',
                                animation: {
                                    open: {height: 'toggle'},
                                    close: {height: 'toggle'},
                                    easing: 'swing', // easing
                                    speed: 500
                                },
                                timeout: 1000
                            })
                        }
                        else {
                            var n = noty({
                                layout: 'top',
                                theme: 'relax',
                                type: 'success',
                                text: '${flash.message}',
                                animation: {
                                    open: {height: 'toggle'},
                                    close: {height: 'toggle'},
                                    easing: 'swing', // easing
                                    speed: 500
                                },
                                timeout: 1000
                            });
                        }
                    }
                })
            }
        }
    </script>
</head>

<body>
<div class="jumbotron head">
    <h1 class="text-center">Accent Tutor</h1>
</div>
<div class="container-fluid">


    <div class="row">
        <div class="col-md-12">
        </div>
    </div>
    <input type="hidden" id="fileName">
    <div class="row">
        <div class="col-md-2">
        </div>
        <div class="col-md-8">
            <table class="table table-responsive">
                <tbody>
                <tr>
                    <td>Namaste</td>
                    <td>
                        <a href="mediaOfSounds/one.mp3">
                            <button type="button" class="btn btn-default btn1">
                                <span class="glyphicon glyphicon-record "></span>  Play
                            </button>
                        </a>
                    </td>
                    <td>
                        <g:link controller="MFCC" action="index" params="[name:'Hello World']">
                            <button type="button" class="btn btn-default btn1">
                                <span class="glyphicon glyphicon-record "></span>  Features
                            </button>
                        </g:link>
                    </td>
                    <td>
                        <button type="button" class="btn btn-default btn1" data-toggle="modal" data-target="#myModal" onclick='changeName("Hello World");'>
                            <span class="glyphicon glyphicon-record "></span>  record
                        </button>
                    </td>
                    </tr>
                <tr>
                    <td>Dhanyabaad</td>
                    <td>
                        <a href="mediaOfSounds/one.mp3">
                            <button type="button" class="btn btn-default btn1">
                                <span class="glyphicon glyphicon-record "></span>  Play
                            </button>
                        </a>
                    </td>
                    <td>
                        <g:link controller="MFCC" action="index" params="[name:'Namaste']">
                            <button type="button" class="btn btn-default btn1">
                                <span class="glyphicon glyphicon-record "></span>  Features
                            </button>
                        </g:link>
                    </td>
                    <td>
                        <button type="button" class="btn btn-default btn1" data-toggle="modal" data-target="#myModal" onclick='changeName("Namaste")'>
                            <span class="glyphicon glyphicon-record "></span>  record
                        </button>
                    </td>
                    </tr>
                </tbody>
            </table>
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