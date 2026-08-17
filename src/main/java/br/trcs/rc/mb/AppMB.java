package br.trcs.rc.mb;

import br.trcs.rc.utils.Consts;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named(Consts.APP_MB)
@ApplicationScoped
public class AppMB {
	public String getReadingClub() {
        return Consts.READING_CLUB;
    }
	
	public String getLogin() {
        return Consts.LOGIN;
    }
	
	public String getLogout() {
        return Consts.LOGOUT;
    }
	
	public String getHome() {
        return Consts.HOME;
    }
	
	public String getAddUsers() {
        return Consts.ADD_USERS;
    }
	
	public String getRegisterUser() {
        return Consts.REGISTER_USER;
    }
	
	public String getAddComics() {
        return Consts.ADD_COMICS;
    }
	
	public String getRegisterComic() {
        return Consts.REGISTER_COMIC;
    }
	
	public String getListComics() {
        return Consts.LIST_COMICS;
    }
	
	public String getAddBoxes() {
        return Consts.ADD_BOXES;
    }
	
	public String getRegisterBox() {
        return Consts.REGISTER_BOX;
    }
	
	public String getAddBorrowings() {
        return Consts.ADD_BORROWINGS;
    }
	
	public String getRegisterBorrowing() {
        return Consts.REGISTER_BORROWING;
    }
	
	public String getBorrowingsHistory() {
        return Consts.BORROWINGS_HISTORY;
    }
	
	public String getDoBorrow() {
        return Consts.DO_BORROW;
    }
	
	public String getGenerateReport() {
        return Consts.GENERATE_REPORT;
    }
	
	public String getLoginJs() {
        return Consts.LOGIN_JS;
    }
	
	public String getMessagesJs() {
        return Consts.MESSAGES_JS;
    }
	
	public String getMenuHtml() {
        return Consts.MENU_HTML;
    }
	
	public String getHeaderHtml() {
        return Consts.HEADER_HTML;
    }
}
