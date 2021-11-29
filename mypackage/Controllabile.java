package mypackage;

import java.beans.PropertyChangeListener;

public interface Controllabile 
{
	void addPropertyChangeListener(PropertyChangeListener listener);
	void removePropertyChangeListener(PropertyChangeListener listener);
}
