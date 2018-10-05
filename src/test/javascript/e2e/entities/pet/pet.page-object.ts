import { element, by, ElementFinder } from 'protractor';

export class PetComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-pet div table .btn-danger'));
    title = element.all(by.css('jhi-pet div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PetUpdatePage {
    pageTitle = element(by.id('jhi-pet-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    speciesInput = element(by.id('field_species'));
    ownerSelect = element(by.id('field_owner'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setSpeciesInput(species) {
        await this.speciesInput.sendKeys(species);
    }

    async getSpeciesInput() {
        return this.speciesInput.getAttribute('value');
    }

    async ownerSelectLastOption() {
        await this.ownerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async ownerSelectOption(option) {
        await this.ownerSelect.sendKeys(option);
    }

    getOwnerSelect(): ElementFinder {
        return this.ownerSelect;
    }

    async getOwnerSelectedOption() {
        return this.ownerSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class PetDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-pet-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-pet'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
