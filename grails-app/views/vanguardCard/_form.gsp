<%@ page import="com.trinary.tcg.cards.VanguardCard" %>
<%@ page import="com.trinary.tcg.cards.Image" %>
<div class="card-area">
  <div class="card-preview">
    <g:if test="${vanguardCardInstance}">
      <img src="${createLink(action: 'generate', id: vanguardCardInstance.id)}" />
    </g:if>
  </div>
  <div class="card-stats">
    <div class="fieldcontain ${hasErrors(bean: vanguardCardInstance, field: 'name', 'error')} ">
            <label for="name">
                    <g:message code="vanguardCard.name.label" default="Name" />

            </label>
            <g:textField name="name" value="${vanguardCardInstance?.name}"/>
    </div>
    
    <div class="fieldcontain ${hasErrors(bean: vanguardCardInstance, field: 'grade', 'error')} required">
            <label for="grade">
                    <g:message code="vanguardCard.grade.label" default="Grade" />
                    <span class="required-indicator">*</span>
            </label>
            <g:field name="grade" type="number" value="${vanguardCardInstance.grade}" required=""/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: vanguardCardInstance, field: 'power', 'error')} required">
            <label for="power">
                    <g:message code="vanguardCard.power.label" default="Power" />
                    <span class="required-indicator">*</span>
            </label>
            <g:field name="power" type="number" value="${vanguardCardInstance.power}" required=""/>
    </div>
    
    <div class="fieldcontain ${hasErrors(bean: vanguardCardInstance, field: 'trigger', 'error')} ">
            <label for="trigger">
                    <g:message code="vanguardCard.trigger.label" default="Trigger" />

            </label>
            <g:select id="trigger" name="trigger.id" from="${com.trinary.tcg.cards.TriggerEffect.list()}" optionKey="id" value="${vanguardCardInstance?.trigger?.id}" class="many-to-one" noSelection="['null': '']"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: vanguardCardInstance, field: 'shield', 'error')} ">
            <label for="shield">
                    <g:message code="vanguardCard.shield.label" default="Shield" />

            </label>
            <g:field name="shield" type="number" value="${vanguardCardInstance.shield}"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: vanguardCardInstance, field: 'crit', 'error')} required">
            <label for="crit">
                    <g:message code="vanguardCard.crit.label" default="Crit" />
                    <span class="required-indicator">*</span>
            </label>
            <g:field name="crit" type="number" value="${vanguardCardInstance.crit}" required=""/>
    </div>
    
    <div class="fieldcontain ${hasErrors(bean: vanguardCardInstance, field: 'clan', 'error')} ">
            <label for="clan">
                    <g:message code="vanguardCard.clan.label" default="Clan" />

            </label>
            <g:textField name="clan" value="${vanguardCardInstance?.clan}"/>
    </div>
    
    <div class="fieldcontain ${hasErrors(bean: vanguardCardInstance, field: 'race', 'error')} ">
            <label for="race">
                    <g:message code="vanguardCard.race.label" default="Race" />

            </label>
            <g:textField name="race" value="${vanguardCardInstance?.race}"/>
    </div>
    
    <div class="fieldcontain ${hasErrors(bean: vanguardCardInstance, field: 'flavorText', 'error')} ">
            <label for="flavorText">
                    <g:message code="vanguardCard.flavorText.label" default="Flavor Text" />

            </label>
            <g:textField name="flavorText" value="${vanguardCardInstance?.flavorText}"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: vanguardCardInstance, field: 'effects', 'error')} ">
            <label for="effects">
                    <g:message code="vanguardCard.effects.label" default="Effects" />

            </label>

      <g:textArea name="effects" id="effects"><g:each in="${vanguardCardInstance?.effects?}" var="e">~${e.toString() + "\n\n"}</g:each></g:textArea>
    </div>
    
    <div class="fieldcontain ${hasErrors(bean: vanguardCardInstance, field: 'artworkFile', 'error')} ">
            <label for="artworkFile">
                    <g:message code="vanguardCard.artworkFile.label" default="Artwork File" />

            </label>
            <%
              List artworks = []
              
              Image.list().each {
                artworks += [name: it.name, file: it.localPath]
              }
            %>
            <g:select name="artworkFile" from="${artworks*.name}" keys="${artworks*.file}" value="${vanguardCardInstance?.artworkFile}" />
    </div>
  </div>
</div>