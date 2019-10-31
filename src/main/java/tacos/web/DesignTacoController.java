package tacos.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import tacos.Taco;
import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;
import tacos.repository.IngredientRepository;



@Slf4j 
/*
 * 使用log.info(msg)方法lombok库提供的注释， 等价于private static final org.slf4j.logger log =
 * org.slf4j.logger.LoggerFactory.getlogger(DesignTacoController.class)
 */
@Controller
@RequestMapping("/design")
public class DesignTacoController {

//private IngredientRepository ingredientRepo;
//
//   @Autowired
//   public DesignTacoController(IngredientRepository ingredientRepo) {
//	   this.ingredientRepo=ingredientRepo;
//   }

@ModelAttribute

	/*
	 * public @interface ModelAttribute Annotation that binds a method parameter or
	 * method return value to a named model attribute, exposed to a web view.
	 * Supported for controller classes with @RequestMapping methods.
	 * 
	 * Can be used to expose command objects to a web view, using specific attribute
	 * names, through annotating corresponding parameters of an @RequestMapping
	 * method.
	 * 
	 * 
	 * Can also be used to expose reference data to a web view through 
	 * annotating accessor methods in a controller class with @RequestMapping methods.
	 *  Such accessor methods are allowed to have any arguments that 
	 *  @RequestMapping methods support, returning the model attribute value to expose. 
	 * 
	 */


public void addIngredientsToModel(Model model) {

		/*
		 * public interface Model

Java-5-specific interface that defines a holder for model attributes. 
Primarily designed for adding attributes to the model.
 Allows for accessing the overall model as a java.util.Map.
		 * addAttribute
		 * 
		 * Model addAttribute(String attributeName,
		 * 
		 * @Nullable Object attributeValue)
		 * 
		 * Add the supplied attribute under the supplied name.
		 * 
		 * Parameters: attributeName - the name of the model attribute (never null)
		 * attributeValue - the model attribute value (can be null)
		 */


	List<Ingredient> ingredients = Arrays.asList(
	  new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
	  new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
	  new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
	  new Ingredient("CARN", "Carnitas", Type.PROTEIN),
	  new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
	  new Ingredient("LETC", "Lettuce", Type.VEGGIES),
	  new Ingredient("CHED", "Cheddar", Type.CHEESE),
	  new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
	  new Ingredient("SLSA", "Salsa", Type.SAUCE),
	  new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
	);
		/*
		 * 定义一个类型为Ingredient的数组列表对象变名为ingredients,通过Arrays类的asList()方法进行赋值； 
		 * public static <T> List<T> asList(T...a)，asList参数为类型的数组，返回值为类型为T的数组列表。
		 * 此处由于没有使用数据库而生成的硬编码，用于填充model的attribute,servlet会复制model.attribute值并转给view.
		 */
	
	Type[] types = Ingredient.Type.values();
	for (Type type : types) {
		  model.addAttribute(type.toString().toLowerCase(),
		      filterByType(ingredients, type));
		}
	
		/*
		 * 定义一个Type型的数组，用Ingredient.Type.values()值赋值，Type是Ingredient类中的enum类型的成量变量，
		 * values()方法， The enum declaration defines a class (called an enum type). The
		 * enum class body can include methods and other fields. The compiler
		 * automatically adds some special methods when it creates an enum. For example,
		 * they have a static values method that returns an array containing all of the
		 * values of the enum in the order they are declared. This method is commonly
		 * used in combination with the for-each construct to iterate over the values of
		 * an enum type. For example, this code from the Planet class example below
		 * iterates over all the planets in the solar system.
		 * 
		 * for (Planet p : Planet.values()) {
		 * System.out.printf("Your weight on %s is %f%n", p, p.surfaceWeight(mass)); }
		 */

	
}
	
  @GetMapping
  public String showDesignForm(Model model) {
//	   List<Ingredient> ingredients = new ArrayList<>();
//	   ingredientRepo.findAll().forEach(i->ingredients.add(i));
//	   
//		
//		Type[] types = Ingredient.Type.values();
//		for (Type type : types) {
//			  model.addAttribute(type.toString().toLowerCase(),
//			      filterByType(ingredients, type));
//		}
//	  
	  
    model.addAttribute("design", new Taco());
		/*
		 * 此处定义了一个model，用于接收“design”中Form的数据，在“design”中需要通过<form
		 * th:object="${design}">进行绑定。 一个Controller中可以定义多个model.
		 */
    return "design";
  }


  
  @PostMapping
  public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors) {
//   方法参数中接收design数据
//	  @Valid是java Validation java bean api 注释作为参数，使得在post时对提交的数进行有效验证
	  if (errors.hasErrors()) {
      return "design";
    }

  
    log.info("Processing design: " + design);
//    调用lombok中的日志方法输出消息
    return "redirect:/orders/current";
//    重定向至此路径
  }


  private List<Ingredient> filterByType(
      List<Ingredient> ingredients, Type type) {
    return ingredients
              .stream()
              .filter(x -> x.getType().equals(type))
              .collect(Collectors.toList());
  }
	/*
	 * 集合的聚合操作.
	 * 自定义方法filterByType实现按类型过滤，返回类型为type的所有记录，由于.collect(Collectors.tolist)实现现归 The
	 * collect operation is best suited for collections. The following example puts
	 * the names of the male members in a collection with the collect operation:
	 * 
	 * List<String> namesOfMaleMembersCollect = roster .stream() .filter(p ->
	 * p.getGender() == Person.Sex.MALE) .map(p -> p.getName())
	 * .collect(Collectors.toList());
	 * 
	 * This version of the collect operation takes one parameter of type Collector.
	 * This class encapsulates the functions used as arguments in the collect
	 * operation that requires three arguments (supplier, accumulator, and combiner
	 * functions).
	 * 
	 * The Collectors class contains many useful reduction operations, such as
	 * accumulating elements into collections and summarizing elements according to
	 * various criteria. These reduction operations return instances of the class
	 * Collector, so you can use them as a parameter for the collect operation.
	 * 
	 * This example uses the Collectors.toList operation, which accumulates the
	 * stream elements into a new instance of List. As with most operations in the
	 * Collectors class, the toList operator returns an instance of Collector, not a
	 * collection.
	 */
}

