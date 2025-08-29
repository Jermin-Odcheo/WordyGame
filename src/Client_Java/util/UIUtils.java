package Client_Java.util;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** UI helpers for responsiveness and a modern feel. */
public class UIUtils {
    private static final ExecutorService IO_POOL =
            Executors.newFixedThreadPool(Math.max(2, Runtime.getRuntime().availableProcessors()/2));

    /** Run work off the EDT and deliver the result back on the EDT. */
    public static <T> void runAsync(Supplier<T> work, Consumer<T> ui) {
        IO_POOL.submit(() -> {
            T result = null;
            try { result = work.get(); } catch (Throwable t) { t.printStackTrace(); }
            final T r = result;
            SwingUtilities.invokeLater(() -> ui.accept(r));
        });
    }

    /** Fire-and-forget background work. */
    public static void runAsync(Runnable work) {
        IO_POOL.submit(() -> { try { work.run(); } catch (Throwable t) { t.printStackTrace(); } });
    }

    /** Simple debouncer to coalesce rapid events (e.g., text changes). */
    public static class Debouncer {
        private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        private final long delayMs;
        private volatile ScheduledFuture<?> pending;

        public Debouncer(long delayMs) {
            this.delayMs = delayMs;
        }

        public synchronized void call(Runnable r) {
            if (pending != null) pending.cancel(false);
            pending = scheduler.schedule(() -> SwingUtilities.invokeLater(r), delayMs, TimeUnit.MILLISECONDS);
        }
    }

    /** Apply friendly Nimbus tweaks (built-in LAF) for a cleaner look without extra libraries. */
    public static void applyModernNimbusTweaks() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignore) {}

        // Subtle padding, font and focus changes
        Font base = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("defaultFont", base);
        UIManager.put("Button.font", base);
        UIManager.put("Label.font", base);
        UIManager.put("TextField.font", base);
        UIManager.put("TextArea.font", base);
        UIManager.put("List.font", base);
        UIManager.put("Table.font", base);
        UIManager.put("nimbusFocus", new Color(120, 120, 220));
    }
}
