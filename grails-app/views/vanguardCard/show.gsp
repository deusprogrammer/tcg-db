
<%@ page import="com.trinary.tcg.cards.VanguardCard" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'vanguardCard.label', default: 'VanguardCard')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-vanguardCard" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-vanguardCard">
                  <h1><g:message code="default.show.label" args="[entityName]" /></h1>
                  <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                  </g:if>

                  <div class="card-area">
                    <div class="card-preview">
                      <img src="${createLink(action: 'generate', id: vanguardCardInstance.id)}" />
                    </div>
                    <div class="card-stats">
                      <table>
                        <g:if test="${vanguardCardInstance?.name}">
                          <tr><td><g:message code="vanguardCard.name.label" default="Name" /></td><td><g:fieldValue bean="${vanguardCardInstance}" field="name"/></td></tr>
                        </g:if>
                        
                        <g:if test="${vanguardCardInstance?.grade}">
                          <tr><td><g:message code="vanguardCard.grade.label" default="Grade" /></td><td><g:fieldValue bean="${vanguardCardInstance}" field="grade"/></td></tr>
                        </g:if>

                        <g:if test="${vanguardCardInstance?.power}">
                          <tr><td><g:message code="vanguardCard.power.label" default="Power" /></td><td><g:fieldValue bean="${vanguardCardInstance}" field="power"/></td></tr>
                        </g:if>
                        
                        <g:if test="${vanguardCardInstance?.trigger}">
                          <tr><td><g:message code="vanguardCard.trigger.label" default="Trigger" /></td><td><g:link controller="triggerEffect" action="show" id="${vanguardCardInstance?.trigger?.id}">${vanguardCardInstance?.trigger?.encodeAsHTML()}</g:link></td></tr>
                        </g:if>

                        <g:if test="${vanguardCardInstance?.shield}">
                          <tr><td><g:message code="vanguardCard.shield.label" default="Shield" /></td><td><g:fieldValue bean="${vanguardCardInstance}" field="shield"/></td></tr>
                        </g:if>

                        <g:if test="${vanguardCardInstance?.crit}">
                          <tr><td><g:message code="vanguardCard.crit.label" default="Crit" /></td><td><g:fieldValue bean="${vanguardCardInstance}" field="crit"/></td></tr>
                        </g:if>
                        
                        <g:if test="${vanguardCardInstance?.clan}">
                          <tr><td><g:message code="vanguardCard.clan.label" default="Clan" /></td><td><g:fieldValue bean="${vanguardCardInstance}" field="clan"/></td></tr>
                        </g:if>

                        <g:if test="${vanguardCardInstance?.race}">
                          <tr><td><g:message code="vanguardCard.race.label" default="Race" /></td><td><g:fieldValue bean="${vanguardCardInstance}" field="race"/></td></tr>
                        </g:if>
                        
                        <g:if test="${vanguardCardInstance?.effects}">
                          <tr><td><g:message code="vanguardCard.effects.label" default="Effects" /></td><td>
                            <g:each in="${vanguardCardInstance.effects}" var="e">
                              ~${e.toString()}<br>
                            </g:each>
                          </td></tr>

                        </g:if>

                        <g:if test="${vanguardCardInstance?.artworkFile}">
                          <tr><td><g:message code="vanguardCard.artworkFile.label" default="Artwork File" /></td><td><g:fieldValue bean="${vanguardCardInstance}" field="artworkFile"/></td></tr>
                        </g:if>
                      </table>
                    </div>
                  </div>
                  <g:form>
                    <fieldset class="buttons">
                      <g:hiddenField name="id" value="${vanguardCardInstance?.id}" />
                      <g:link class="edit" action="edit" id="${vanguardCardInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                      <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </fieldset>
                  </g:form>
		</div>
	</body>
</html>
