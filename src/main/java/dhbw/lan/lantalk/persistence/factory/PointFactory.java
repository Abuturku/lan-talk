package dhbw.lan.lantalk.persistence.factory;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

import dhbw.lan.lantalk.persistence.objects.Point;
import dhbw.lan.lantalk.persistence.objects.TextComponent;
import dhbw.lan.lantalk.persistence.objects.User;

@Named("pointFactory")
@Dependent
public class PointFactory extends AFactory<Point> {

	private static final long serialVersionUID = 5223761142006971721L;

	public PointFactory() {
		super(Point.class);
	}

	public Point create(Point point, User user, TextComponent textComponent) {
		// point.setTextComponent(textComponent);
		// point.setUser(user);
		// user.addPoints(point);
		// textComponent.addPoint(point);
		return super.create(point);

	}
}
