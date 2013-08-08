package com.trinary.tcg.cards



import org.junit.*
import grails.test.mixin.*

@TestFor(VanguardCardController)
@Mock(VanguardCard)
class VanguardCardControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/vanguardCard/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.vanguardCardInstanceList.size() == 0
        assert model.vanguardCardInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.vanguardCardInstance != null
    }

    void testSave() {
        controller.save()

        assert model.vanguardCardInstance != null
        assert view == '/vanguardCard/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/vanguardCard/show/1'
        assert controller.flash.message != null
        assert VanguardCard.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/vanguardCard/list'

        populateValidParams(params)
        def vanguardCard = new VanguardCard(params)

        assert vanguardCard.save() != null

        params.id = vanguardCard.id

        def model = controller.show()

        assert model.vanguardCardInstance == vanguardCard
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/vanguardCard/list'

        populateValidParams(params)
        def vanguardCard = new VanguardCard(params)

        assert vanguardCard.save() != null

        params.id = vanguardCard.id

        def model = controller.edit()

        assert model.vanguardCardInstance == vanguardCard
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/vanguardCard/list'

        response.reset()

        populateValidParams(params)
        def vanguardCard = new VanguardCard(params)

        assert vanguardCard.save() != null

        // test invalid parameters in update
        params.id = vanguardCard.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/vanguardCard/edit"
        assert model.vanguardCardInstance != null

        vanguardCard.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/vanguardCard/show/$vanguardCard.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        vanguardCard.clearErrors()

        populateValidParams(params)
        params.id = vanguardCard.id
        params.version = -1
        controller.update()

        assert view == "/vanguardCard/edit"
        assert model.vanguardCardInstance != null
        assert model.vanguardCardInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/vanguardCard/list'

        response.reset()

        populateValidParams(params)
        def vanguardCard = new VanguardCard(params)

        assert vanguardCard.save() != null
        assert VanguardCard.count() == 1

        params.id = vanguardCard.id

        controller.delete()

        assert VanguardCard.count() == 0
        assert VanguardCard.get(vanguardCard.id) == null
        assert response.redirectedUrl == '/vanguardCard/list'
    }
}
