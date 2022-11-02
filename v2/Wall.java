import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

interface Structure {
    // zwraca dowolny element o podanym kolorze
    Optional<Block> findBlockByColor(String color);

    // zwraca wszystkie elementy z danego materiału
    List<Block> findBlocksByMaterial(String material);

    //zwraca liczbę wszystkich elementów tworzących strukturę
    int count();

}

public class Wall implements Structure {

    private List<Block> blocks;

    @Override
    public Optional<Block> findBlockByColor(String color) {

        for(Block bl:blocks){
            Block res=recursiveSearchColor(bl,color);

            if(res!=null){
                return Optional.of(res);
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {

        List<Block> returnList = new ArrayList<>();

        for(Block bl:blocks){
            returnList=recursiveSearchMaterial(bl,material,returnList);
        }

        return returnList;
    }

    @Override
    public int count() {

        int counter=0;
        for(Block bl:blocks){
            if(bl instanceof CompositeBlock){
                counter=recursiveCount(counter,(CompositeBlock) bl);
            }
            else{
                counter++;
            }
        }

        return counter;
    }

    public int recursiveCount(int count, CompositeBlock block) {

        for(Block bl:block.getBlocks()){

            if(bl instanceof CompositeBlock){
                count=recursiveCount(count,(CompositeBlock) bl);
            }else {
                ++count;
            }
        }
        return count;
    }


    private Block recursiveSearchColor(Block block, String color){

        Block result;

        if(block instanceof CompositeBlock){
            for(Block bl:((CompositeBlock) block).getBlocks()){
                result = recursiveSearchColor(bl, color);

                if(result!=null){
                    return result;
                }
            }
        }
        else{
            if(block.getColor().equals(color)){
                return block;
            }

        }

        return null;

    }


    private List<Block> recursiveSearchMaterial(Block block, String material,List<Block> returnList){

        if(block instanceof CompositeBlock){
            for(Block bl:((CompositeBlock) block).getBlocks()){
                returnList = recursiveSearchMaterial(bl, material, returnList);
            }
        }
        else{
            if(block.getMaterial().equals(material)){
                returnList.add(block);
                return returnList;
            }

        }

        return returnList;

    }


}

interface Block {
    String getColor();

    String getMaterial();
}

interface CompositeBlock extends Block {
    List<Block> getBlocks();

    void addBlockToList(Block block);

}



