package javaprograms;

//https://www.reddit.com/r/dailyprogrammer/comments/4cb7eh/20160328_challenge_260_easy_garage_door_opener/

public class EasyGarageDoorOpener {

    private static final String OPENED = "opened";
    private static final String CLOSED = "closed";
    private static final String OPENING = "opening";
    private static final String CLOSING = "closing";
    private static final String STOPPED_WHILE_OPENING = "stopped_while_opening";
    private static final String STOPPED_WHILE_CLOSING = "stopped_while_closing";

    private static final String BUTTON_CLICKED = "button_clicked";
    private static final String CYCLE_COMPLETE = "cycle_complete";

    private static String position = CLOSED;

    private static String[] commands = {
            BUTTON_CLICKED,
            CYCLE_COMPLETE,
            BUTTON_CLICKED,
            BUTTON_CLICKED,
            BUTTON_CLICKED,
            BUTTON_CLICKED,
            BUTTON_CLICKED,
            CYCLE_COMPLETE
    };

    public static void main(String[] args){
        System.out.println("Door : " + position);
        for(int i=0; i< commands.length; i++) {
            doorState(commands[i]);
        }
    }

    private static void doorState(String arg){
        System.out.println(" >> " + arg);
        if(CYCLE_COMPLETE.equalsIgnoreCase(arg)){
            if(position.equalsIgnoreCase(OPENING)) {
                position = OPENED;
            } else {
                position = CLOSED;
            }
        }

        if(BUTTON_CLICKED.equalsIgnoreCase(arg)){
            if(position.equalsIgnoreCase(CLOSED)){
                position = OPENING;
            } else if (position.equalsIgnoreCase(STOPPED_WHILE_CLOSING)){
                position = OPENING;
            } else if (position.equalsIgnoreCase(OPENING)){
                position = STOPPED_WHILE_OPENING;
            } else if (position.equalsIgnoreCase(STOPPED_WHILE_OPENING)){
                position = CLOSING;
            } else if (position.equalsIgnoreCase(CLOSING)){
                position = STOPPED_WHILE_CLOSING;
            } else if (position.equalsIgnoreCase(OPENED)){
                position = CLOSING;
            }
        }
        System.out.println("Door : " + position);
    }
}
