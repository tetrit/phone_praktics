import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "superheroes")
public class SuperHero {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String power;
    public int level;

    public SuperHero(String name, String power, int level) {
        this.name = name;
        this.power = power;
        this.level = level;
    }
}