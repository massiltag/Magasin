package graphic.edit;
import java.awt.*;  
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import personnel.Employe;

@SuppressWarnings("serial")
public class ChangePassword extends JDialog implements ActionListener {
	JPanel main,form,buttons;
	JLabel loginL,pwdL,confirmL;
	JTextField login;
	JPasswordField pwd,confirm;
	JButton ok,cancel;
	Employe user;
	
	public ChangePassword(Employe user) {
		this.user = user;
		this.setTitle("Changement de mot de passe");
		this.setBounds(400,250,340,180);
		
		main = new JPanel(new BorderLayout());
		form = new JPanel(new GridLayout(3,2,3,10));
		buttons = new JPanel(new GridLayout(1,2,4,0));
		form.setBorder(new EmptyBorder(5,5,5,5));
		buttons.setBorder(new EmptyBorder(5,5,5,5));
		
		loginL = new JLabel("Nouveau login");		login = new JTextField();			login.setText(this.user.getLogin());	login.setEditable(false);
		pwdL = new JLabel("Nouveau mot de passe");	pwd = new JPasswordField();
		confirmL = new JLabel("Confirmation");		confirm = new JPasswordField();
		ok = new JButton("OK");						ok.addActionListener(this);
		cancel = new JButton("Annuler");			cancel.addActionListener(this);
		
		form.add(loginL);							form.add(login);
		form.add(pwdL);								form.add(pwd);
		form.add(confirmL);							form.add(confirm);
		buttons.add(ok);							buttons.add(cancel);
		
		main.add(new JLabel("Utilisateur : " + this.user.getNom()),BorderLayout.NORTH);
		main.add(form,BorderLayout.CENTER);
		main.add(buttons,BorderLayout.SOUTH);
		
		this.setContentPane(main);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==ok) {
			if (this.check()) {
				String strpwd = new String(this.pwd.getPassword());
				String strconfirm = new String(this.confirm.getPassword());
				if (strpwd.equals(strconfirm)) {
					this.user.setLogin(this.login.getText());
					this.user.setPassword(new String(this.pwd.getPassword()));
					this.alert("Identifiants changés");
					this.dispose();
				} else {
					this.alert("Les mots de passe saisis ne correspondent pas.");
				}
			}
		}
		if (e.getSource()==cancel) {
			this.dispose();
		}
	}
	
	public boolean check() {
		String strpwd = new String(this.pwd.getPassword());
		String strconfirm = new String(this.confirm.getPassword());
		if ((login.getText().equals("")) || (strpwd.equals("")) || (strconfirm.equals(""))) {
			this.alert("Un ou plusieurs champs invalides.");
			return false;
		} else return true;
	}
	
	public void alert(String s) {
		JDialog.setDefaultLookAndFeelDecorated(true);
		javax.swing.JOptionPane.showMessageDialog(null,s); 
	}
}
