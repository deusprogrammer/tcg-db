
<%@ page import="com.trinary.tcg.cards.VanguardCard" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'vanguardCard.label', default: 'VanguardCard')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-vanguardCard" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-vanguardCard" class="content scaffold-list" role="main">
                  <h1><g:message code="default.list.label" args="[entityName]" /></h1>
                  <g:if test="${flash.message}">
                  <div class="message" role="status">${flash.message}</div>
                  </g:if>
                    <div class="card-row">
                      <g:each in="${vanguardCardInstanceList}" status="i" var="vanguardCardInstance">
                        <g:link action="show" id="${vanguardCardInstance.id}">
                          <div class="card-container">
                            <div class="card-image">
                              <img src="${createLink(action: 'generate', id: vanguardCardInstance.id)}" width="200"/>
                            </div>
                            <div class="card-name">
                              ${fieldValue(bean: vanguardCardInstance, field: "name")}
                            </div>
                          </div>
                        </g:link>
                      </g:each>
                    </div>
                  <div class="pagination">
                          <g:paginate total="${vanguardCardInstanceTotal}" />
                  </div>
		</div>
	</body>
</html>
