package com.emailManager.logicService;

/**
 * Multi-threaded object class to send out emails in parallel(Asynchronously)
 */
public class SendEmailThread implements Runnable{
    private OutBox outBox;

    /**
     * Custom constructor
     * @param outBox
     */
    public SendEmailThread(OutBox outBox) {
        this.outBox = outBox;
    }

    /**
     * Thread run(): Sends out email from the OutBox.send() method
     */
    @Override
    public void run() {
        outBox.sendEmail();
    }
}
