/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PetComponentsPage, PetDeleteDialog, PetUpdatePage } from './pet.page-object';

const expect = chai.expect;

describe('Pet e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let petUpdatePage: PetUpdatePage;
    let petComponentsPage: PetComponentsPage;
    let petDeleteDialog: PetDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Pets', async () => {
        await navBarPage.goToEntity('pet');
        petComponentsPage = new PetComponentsPage();
        expect(await petComponentsPage.getTitle()).to.eq('jhipsterSandBoxApp.pet.home.title');
    });

    it('should load create Pet page', async () => {
        await petComponentsPage.clickOnCreateButton();
        petUpdatePage = new PetUpdatePage();
        expect(await petUpdatePage.getPageTitle()).to.eq('jhipsterSandBoxApp.pet.home.createOrEditLabel');
        await petUpdatePage.cancel();
    });

    it('should create and save Pets', async () => {
        const nbButtonsBeforeCreate = await petComponentsPage.countDeleteButtons();

        await petComponentsPage.clickOnCreateButton();
        await petUpdatePage.setNameInput('name');
        expect(await petUpdatePage.getNameInput()).to.eq('name');
        await petUpdatePage.setSpeciesInput('species');
        expect(await petUpdatePage.getSpeciesInput()).to.eq('species');
        await petUpdatePage.ownerSelectLastOption();
        await petUpdatePage.save();
        expect(await petUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await petComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Pet', async () => {
        const nbButtonsBeforeDelete = await petComponentsPage.countDeleteButtons();
        await petComponentsPage.clickOnLastDeleteButton();

        petDeleteDialog = new PetDeleteDialog();
        expect(await petDeleteDialog.getDialogTitle()).to.eq('jhipsterSandBoxApp.pet.delete.question');
        await petDeleteDialog.clickOnConfirmButton();

        expect(await petComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
