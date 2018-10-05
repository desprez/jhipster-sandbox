/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OwnerComponentsPage, OwnerDeleteDialog, OwnerUpdatePage } from './owner.page-object';

const expect = chai.expect;

describe('Owner e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let ownerUpdatePage: OwnerUpdatePage;
    let ownerComponentsPage: OwnerComponentsPage;
    let ownerDeleteDialog: OwnerDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Owners', async () => {
        await navBarPage.goToEntity('owner');
        ownerComponentsPage = new OwnerComponentsPage();
        expect(await ownerComponentsPage.getTitle()).to.eq('jhipsterSandBoxApp.owner.home.title');
    });

    it('should load create Owner page', async () => {
        await ownerComponentsPage.clickOnCreateButton();
        ownerUpdatePage = new OwnerUpdatePage();
        expect(await ownerUpdatePage.getPageTitle()).to.eq('jhipsterSandBoxApp.owner.home.createOrEditLabel');
        await ownerUpdatePage.cancel();
    });

    it('should create and save Owners', async () => {
        const nbButtonsBeforeCreate = await ownerComponentsPage.countDeleteButtons();

        await ownerComponentsPage.clickOnCreateButton();
        await ownerUpdatePage.setNameInput('name');
        expect(await ownerUpdatePage.getNameInput()).to.eq('name');
        await ownerUpdatePage.save();
        expect(await ownerUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await ownerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Owner', async () => {
        const nbButtonsBeforeDelete = await ownerComponentsPage.countDeleteButtons();
        await ownerComponentsPage.clickOnLastDeleteButton();

        ownerDeleteDialog = new OwnerDeleteDialog();
        expect(await ownerDeleteDialog.getDialogTitle()).to.eq('jhipsterSandBoxApp.owner.delete.question');
        await ownerDeleteDialog.clickOnConfirmButton();

        expect(await ownerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
