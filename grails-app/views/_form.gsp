<%@ page import="com.accenttutor.Configuration" %>

<div class="form group ${hasErrors(bean: configurationInstance, field: 'word', 'error')} required">
    <label for="word">
        <g:message code="company.word.label" default="Word" />
        <span class="required-indicator">*</span>
    </label>
    <div class="input-group">
        <span class="input-group-addon" > <span class="glyphicon glyphicon-earphone"></span></span>
        <g:textField class="form-control" name="word" required="" value="${configurationInstance?.word}"/>
    </div>
</div>

<div class="form-group ${hasErrors(bean: configurationInstance, field: 'templateName', 'error')} required">
    <label for="templateName">
        <g:message code="configuration.templateName.label" default="Template Name" />
        <span class="required-indicator">*</span>
    </label>
    <div class="input-group">
        <span class="input-group-addon"> <span class="glyphicon glyphicon-user"></span></span>
        <input type="file" class="file optional" name="templateName" id="templateName" value="${configurationInstance?.templateName}"/>
    </div>
</div>


<div class="form-group ${hasErrors(bean: configurationInstance, field: 'standardPronunciation', 'error')} required">
    <label for="standardPronunciation">
        <g:message code="configuration.standardPronunciation.label" default="Standard Pronunciation" />
        <span class="required-indicator">*</span>
    </label>
    <div class="input-group">
        <span class="input-group-addon"> <span class="glyphicon glyphicon-user"></span></span>
        <input type="file" class="file optional" name="standardPronunciation" id="standardPronunciation" value="${configurationInstance?.standardPronunciation}"/>
    </div>
</div>
