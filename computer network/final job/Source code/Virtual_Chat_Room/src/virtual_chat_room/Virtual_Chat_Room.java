/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtual_chat_room;
/**
 *
 * @author zhaoq
 */
public class Virtual_Chat_Room {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UI_ChatPage chatPage = new UI_ChatPage();
        UI_FrontPage frontPage = new UI_FrontPage();
        frontPage.initialize(chatPage);
        frontPage.setVisible(true);
        // TODO code application logic here
    } 
    
}
