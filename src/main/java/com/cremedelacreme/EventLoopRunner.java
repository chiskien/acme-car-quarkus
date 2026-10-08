package com.cremedelacreme;

public class EventLoopRunner {

    public static void main(String[] args) {
        EventLoop eventLoop = new EventLoop();

        new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                delay(1000);
                eventLoop.dispatch(new Event("tick", i));
            }
            eventLoop.dispatch(new Event("stop", "stop"));
        }).start();

        new Thread(() -> {
            delay(2500);
            eventLoop.dispatch(new Event("hello", "world"));
            delay(800);
            eventLoop.dispatch(new Event("hello", "Ruby"));
        }).start();

        eventLoop.dispatch(new Event("hello", "worlds!"));
        eventLoop.dispatch(new Event("foo", "bar"));
        eventLoop.on("hello", s -> System.out.println("Hello" + s))
            .on("tick", n -> System.out.println("ticket" + n))
            .on("stop", v -> eventLoop.stop());

        System.out.println("Bye");
    }

    private static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
