import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
W ramach implementacji zakładam, że mur składa się zawsze ze złożonych bloków,
nawet jeżeli złożony blok posiada "w sobie" tylko jeden blok prosty.
W przypadku metody findBlockByColor skupiam się na znajdowaniu pierwszego bloku złożonego, składającego się z bloków
prostych o tym samym kolorze - wskazanym przez parametr.
W kontekście metody findBlocksByMaterial znajduję wszystkie bloki złożone o jednolitym, wskazanym materiale.
Metoda count zwraca informację o ilości bloków prostych w murze.
Metoda countComposite zwraca informację o ilości bloków złożonych w murze.
*/
interface Structure {
    // zwraca dowolny element o podanym kolorze
    Optional<CompositeBlock> findBlockByColor(String color);

    // zwraca wszystkie elementy z danego materiału
    List<CompositeBlock> findBlocksByMaterial(String material);

    //zwraca liczbę wszystkich elementów tworzących strukturę
    int count();

    int countComposite();
}

public class Wall implements Structure {

    private List<CompositeBlock> blocks;

    @Override
    public Optional<CompositeBlock> findBlockByColor(String color) {

        boolean blockChecker;

        for (CompositeBlock block : blocks) {

            blockChecker = true;
            for (Block bl : block.getBlocks()) {
                if (!bl.getColor().equals(color)) {
                    blockChecker = false;
                    break;
                }
            }

            if (blockChecker) {
                return Optional.of(block);
            }
        }

        return Optional.empty();
    }

    @Override
    public List<CompositeBlock> findBlocksByMaterial(String material) {

        boolean blockChecker;

        List<CompositeBlock> result = new ArrayList<>();
        for (CompositeBlock block : blocks) {
            blockChecker = true;
            for (Block bl : block.getBlocks()) {
                if (!bl.getMaterial().equals(material)) {
                    blockChecker = false;
                    break;
                }
            }

            if (blockChecker) {
                result.add(block);
            }

        }

        return result;

    }

    @Override
    public int count() {

        int counter = 0;
        for (CompositeBlock block : blocks) {
            counter += block.getBlocks().stream().count();
        }
        return counter;
    }

    @Override
    public int countComposite() {
        return (int) blocks.stream().count();
    }


}

interface Block {
    String getColor();

    String getMaterial();
}

interface CompositeBlock extends Block {
    List<Block> getBlocks();
}