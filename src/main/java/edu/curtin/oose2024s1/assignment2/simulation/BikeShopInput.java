package edu.curtin.oose2024s1.assignment2.simulation;
import java.util.*;

/**
 *
 */
public class BikeShopInput
{
    // These are the probabilities of the various types of messages appearing each time step (or
    // appearing _another time_ after having already appeared).
    private static final double DELIVERY_P = 0.1;
    private static final double DROP_OFF_P = 0.25;
    private static final double PURCHASE_ONLINE_P = 0.4;
    private static final double PURCHASE_IN_STORE_P = 0.4;
    private static final double PICK_UP_P = 0.25;
    private static final double SCREW_UP_P = 0.05;

    private long lastTime = System.currentTimeMillis();
    private Random random = new Random();
    private List<String> emails = new ArrayList<>();

    @SuppressWarnings("PMD.LooseCoupling")  // We call LinkedList.poll(), which List doesn't have.
    private LinkedList<String> messages = new LinkedList<>();

    public BikeShopInput(long seed)
    {
        this.random = new Random(seed);
    }

    public BikeShopInput()
    {
        this(System.currentTimeMillis());
    }

    private void genLetters(StringBuilder sb, char base, int range, int len)
    {
        for(int i = 0; i < len; i++)
        {
            sb.append((char)(base + random.nextInt(range)));
        }
    }

    private String makeEmail()
    {
        var sb = new StringBuilder();
        genLetters(sb, 'a', 26, random.nextInt(5) + 3);
        sb.append('@');
        genLetters(sb, 'a', 26, random.nextInt(5) + 3);
        sb.append('.');
        genLetters(sb, 'a', 26, 3);
        return sb.toString();
    }

    /**
     * Retrieves the next input message, for the bike shop simulation. This method generates these
     * messages randomly as needed.
     */
    public String nextMessage()
    {
        long thisTime = System.currentTimeMillis();
        for(long t = lastTime + 999L; t < thisTime; t += 1000L)
        {
            var newMessages = new ArrayList<String>();
            while(random.nextDouble() < DELIVERY_P)
            {
                newMessages.add("DELIVERY");
            }
            while(random.nextDouble() < DROP_OFF_P)
            {
                var email = makeEmail();
                emails.add(email);
                newMessages.add("DROP-OFF " + email);
            }
            while(random.nextDouble() < PURCHASE_ONLINE_P)
            {
                var email = makeEmail();
                emails.add(email);
                newMessages.add("PURCHASE-ONLINE " + email);
            }
            while(random.nextDouble() < PURCHASE_IN_STORE_P)
            {
                newMessages.add("PURCHASE-IN-STORE");
            }
            while(!emails.isEmpty() && random.nextDouble() < PICK_UP_P)
            {
                newMessages.add("PICK-UP " + emails.get(random.nextInt(emails.size())));
            }
            while(random.nextDouble() < SCREW_UP_P)
            {
                var sb = new StringBuilder();
                genLetters(sb, ' ', 95, random.nextInt(30));
                if(!emails.isEmpty() && random.nextBoolean())
                {
                    sb.append(' ');
                    sb.append(emails.get(random.nextInt(emails.size())));
                }
                newMessages.add(sb.toString());
            }
            Collections.shuffle(newMessages, random);
            messages.addAll(newMessages);
        }
        lastTime = thisTime;
        return messages.poll();
    }
}

