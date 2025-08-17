package view;

public class GameLogger {
    private final StringBuilder log = new StringBuilder();
    private float totalDamageOnPlayer = 0;

    public void appendLog(String string){
        log.append(string).append("\n");
    }

    public void addDamageOnPlayer(float damage){
        totalDamageOnPlayer += damage;
    }

    public void clear(){
        log.setLength(0);
        totalDamageOnPlayer = 0;
    }

    public String getResume(){
        return (log.toString().concat(String.format("\nVocê recebeu %.0f de dano\n", totalDamageOnPlayer)));
    }
}
