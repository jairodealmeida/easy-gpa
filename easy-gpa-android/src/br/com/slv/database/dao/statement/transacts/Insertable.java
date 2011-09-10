package br.com.slv.database.dao.statement.transacts;


public interface Insertable extends Transactionable{

    public StringBuilder getParameters();

}
