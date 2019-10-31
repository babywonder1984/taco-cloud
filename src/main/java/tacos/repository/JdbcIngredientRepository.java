package tacos.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tacos.domain.Ingredient;
@Repository
/*
 * 实现接口的类被注释后，Spring会自动扫描进行装配. 由此在Service层，可以通过@Autowired注释被该类实现的接口，注入该类
 */
public class JdbcIngredientRepository implements IngredientRepository {

	
	private JdbcTemplate jdbc;
	
	
	@Autowired
	public JdbcIngredientRepository(JdbcTemplate jdbc) {
		this.jdbc=jdbc;
	}
	
	@Override
	public Iterable<Ingredient> findAll() {
		
		return jdbc.query("select id, name, type from Ingredient", this::rowMapper);
		/*
		 * 有一个接口，我们需要使用它。我们可以定义一个本地类实现它。然后实例化这个本地类，通过实例调用方法。
		 * 假如我们只需要使用一次，我们就可以利用匿名类表达式，定义的同时实例并使用它。
		 * 如果被实现的接口只有一个方法签名（这种接口也被称为函数式接口），我们可以进一步使用lamda（匿名函数）简化它。
		 * 再次如果这个lamda中的方法已经被实现或者存在，那么可以通过方法调用（特定包含实例：：实例方法名）简化。
		 */
	}

	
	private Ingredient rowMapper(ResultSet rs , int rowNum) throws SQLException {
		 return new Ingredient(rs.getString("id"),
				 rs.getString("name"),
				 Ingredient.Type.valueOf(rs.getString("type")));
				 
	}
	
//	@Override
//	public Ingredient findOne(String id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Ingredient save(Ingredient ingredient) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
