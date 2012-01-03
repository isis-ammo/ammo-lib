package edu.vu.isis.ammo;

/**
 * Convenience interface that can be implemented to help
 * compartmentalize the different setup steps of an activity.
 * @author Demetri Miller
 *
 */
public interface IAmmoActivitySetup {
	public void setViewReferences();
	public void setViewAttributes();
	public void setOnClickListeners();
}
