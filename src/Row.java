public class Row
{
    String[] data;
    
    static Row base = null;
    
    public Row(String s) {
        data = s.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    }
    
    public Row(String[] d) {
        data = d;
    }
    
    public void setBase(Row r) {
        base = r;
    }
    
    public String[] getData() {
        return data;
    }
    
    public String getByName(String n) {
        if(base == null) return "";
        
        String[] baseVals = base.getData();
        
        if(baseVals.length != data.length) return "";
        
        for(int i=0; i<baseVals.length; i++) {
            if(baseVals[i].equals(n)) return data[i];
        }
        
        return "";
    }
    
    public String toString() {
        String toRet = "[ ";
        
        for(String s : data) {
            toRet += s + " | ";
        }
        
        return toRet + " ]";
    }
}