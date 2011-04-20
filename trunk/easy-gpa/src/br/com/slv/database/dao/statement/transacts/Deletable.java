package br.com.slv.database.dao.statement.transacts;

import br.com.slv.database.dao.statement.operation.WhereStatement;

public interface Deletable extends Transactionable{
    public WhereStatement where = new WhereStatement();
    public StringBuilder getParameters();
}
