class Result{
    int max;
    String result;
    public Result(int max, String result){
        this.max = max;
        this.result = result;
    }
    @Override
    public String toString(){
        return "構成員の最大数：" + max + "\n構成員：" + result;
    }
}