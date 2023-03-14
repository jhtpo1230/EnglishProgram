package EnlgishProgram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class mainFrame extends JFrame {
	JLabel title = new JLabel("ÅäÀÍ °øºÎ È­ÀÌÆÃ!", SwingConstants.CENTER);
	JPanel fileaddPanel = new JPanel();
	JButton fileaddButton = new JButton("ÆÄÀÏ Ãß°¡ÇÏ±â");
	JButton wordaddButton = new JButton("´Ü¾î Ãß°¡ÇÏ±â");
	JPanel startPanel = new JPanel();
	JPanel testStartPanel = new JPanel();
	JButton studyStart = new JButton("°øºÎ ½ÃÀÛÈ÷±â");
	JButton testStart = new JButton("½ÃÇè ½ÃÀÛÈ÷±â");
	JPanel fileSelectPanel = new JPanel();
	JButton select = new JButton("ÆÄÀÏ ¼±ÅÃÇÏ±â");
	JLabel fileInformation = new JLabel("¼±ÅÃµÈ ÆÄÀÏ : ");
	int selecting = 0;
	int AddWordNum = 0;
	int TestNum = 1;
	boolean TestFinish = false;

	JTextArea memozang = new JTextArea(20, 10);

	String fileNameString;
	JFileChooser fc = new JFileChooser();;

	class word {
		private String eng;
		private String kor;

		public word(String eng, String kor) {
			this.eng = eng;
			this.kor = kor;
		}

		public String getEng() {
			return eng;
		}

		public String getKor() {
			return kor;
		}
	}

	Vector<word> v = new Vector<word>();

	class wordList {

		public wordList() {
			File file = new File("C:\\Users\\a\\Desktop\\ÀÚ¹Ù2\\¿µ´Ü¾î ÇÁ·Î±×·¥\\¿µ´Ü¾î ÇÁ·Î±×·¥ ´Ü¾îÀå\\" + fileNameString);

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {

				String line;
				int VectorNum = 0;
				ArrayList<String> EngStr = new ArrayList<>();
				ArrayList<String> KorStr = new ArrayList<>();

				while ((line = br.readLine()) != null) {
					String[] splitStr = line.split("\\s+");
					VectorNum++;
					EngStr.add(splitStr[0]);
					KorStr.add(splitStr[1]);
				}

				int m, n;
				int RandomWordArray[] = new int[VectorNum]; // ´Ü¾î ·£´ý Ãâ·ÂÀ» À§ÇÑ ·£´ý ¼ýÀÚ ÀúÀå ¹è¿­
				Random random = new Random();

				for (m = 0; m < VectorNum; m++) { // ´Ü¾î°¡ ´Ù ³ª¿Ã ¼ö ÀÖµµ·Ï Áßº¹ Á¦°Å
					RandomWordArray[m] = random.nextInt(VectorNum);
					for (n = 0; n < m; n++) {
						if (RandomWordArray[m] == RandomWordArray[n]) {
							m--;
						}
					}
				}
				for (m = 0; m < VectorNum; m++) {
					v.add(new word(EngStr.get(RandomWordArray[m]), KorStr.get(RandomWordArray[m])));
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class finalTest extends JFrame implements ActionListener {
		public int num = 5; // ´Ü¾î °³¼ö
		public int test = 0; // testSystem ¹öÆ°¿¡ µû¸¥ º¯È­ÇÏ´Â º¯¼ö

		String[] data = new String[num]; // ³»°¡ ÀûÀº ´äÀ» ÀúÀåÇÒ data ¹è¿­À» Àü¿ªº¯¼ö·Î ¼±¾ð => ¿À´ä³ëÆ®¶§ »ç¿ëÇÏ±â À§ÇØ Àü¿ª

		JButton B_1 = new JButton("     Ã¤Á¡     ");
		JButton B_2 = new JButton(" ¿À´ä³ëÆ® ");
		JButton B_3 = new JButton("   Àç½ÃÇè   ");

		JButton SaveMemo = new JButton("¸Þ¸ðÀå ÀúÀåÇÏ±â");

		JLabel score = new JLabel();

		JButton backButton = new JButton("µ¹¾Æ°¡±â");
		JButton exitProgram = new JButton("Á¾·áÇÏ±â");

		public finalTest() {
			setTitle("¿µ´Ü¾î ¾Ï±â ÇÁ·Î±×·¥");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(400, 800);
			setLayout(new GridLayout(0, 2));

			class WordPanel extends JPanel implements ActionListener {

				JPanel[] OneWordpanel = new JPanel[num];

				wordList WL = new wordList();

				JLabel[] korWord = new JLabel[num];
				JTextField[] engWord = new JTextField[num];

				int i, j;
				int a[] = new int[num]; // ´Ü¾î ·£´ý Ãâ·ÂÀ» À§ÇÑ ·£´ý ¼ýÀÚ ÀúÀå ¹è¿­
				Random random = new Random();

				public WordPanel() {

					for (i = 0; i < num; i++) {
						OneWordpanel[i] = new JPanel();
						OneWordpanel[i].setLayout(new GridLayout(0, 2));
						OneWordpanel[i].add(korWord[i] = new JLabel(v.get(i).getKor()), BorderLayout.CENTER);
						OneWordpanel[i].add(engWord[i] = new JTextField());
					}
					for (i = 0; i < num; i++) { // ´Ü¾î°¡ ´Ù ³ª¿Ã ¼ö ÀÖµµ·Ï Áßº¹ Á¦°Å
						a[i] = random.nextInt(num);
						for (j = 0; j < i; j++) {
							if (a[i] == a[j]) {
								i--;
							}
						}
					}
					for (i = 0; i < num; i++) {
						this.add(OneWordpanel[a[i]]);
						// OneWordPanelÀº 30°³ÀÇ ´Ü¾î ¼ø¼­´ë·Î ÀúÀå, Ãâ·Â¸¸ ¹è¿­ ÀÎµ¦½º °ª¿¡ µû¸¥ ÆÐ³Î ·£´ý Ãâ·Â
					}
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					int correctNum = 0;
					int wrongNum = 0;

					if (e.getSource() == B_1) {
						if (TestFinish == false) {
							int i;

							for (i = 0; i < num; i++) {
								data[i] = engWord[i].getText().strip(); // ³»°¡ ÀûÀº ´ä¾ÈÀ» data ¹è¿­¿¡ ÀúÀå

								if (data[i].equalsIgnoreCase((v.get(i)).getEng()) == true) {
									correctNum++;
								}
							}
							wrongNum = num - correctNum;
							String[] wrong = new String[wrongNum]; // Æ²¸° ¿À´ä ÀúÀåÇÒ wrong ¹è¿­
							for (i = 0; i < wrongNum; i++) {
								if (data[i].equalsIgnoreCase((v.get(i)).getEng()) != true) {
									wrong[i] = data[i];
								}
							}

							double result = (double) correctNum / (double) num * 100;
							String RealScore = String.format("%.1f", result);

							score.setText(RealScore + "Á¡");
							TestFinish = true;

							if (result >= 80)
								test = 2; // ÇÕ°Ý
							else
								test = 1; // ºÒÇÕ°Ý
						} else if (TestFinish == true) {
							JOptionPane notice = new JOptionPane();
							notice.showMessageDialog(null, "Ã¤Á¡ÀÌ Á¾·áµÆÀ¸¹Ç·Î Àç½ÃÇèÇÏ½Ê½Ã¿À.");
						}

					} else if (e.getSource() == B_3) { // Àç½ÃÇèÀ» ´­·¶Áö¸¸
						if (TestNum <= 5) {
							TestFinish = false;

							if (test == 0) { // Ã¤Á¡ÀÌ ¾È µÈ °æ¿ì
								JOptionPane notice = new JOptionPane();
								notice.showMessageDialog(null, "Ã¤Á¡À» ¸ÕÀú ÁøÇàÇÏ½Ê½Ã¿À.");
							} else if (test == 2) { // Ã¤Á¡ °á°ú°¡ ÇÕ°ÝÀÏ °æ¿ì
								JOptionPane notice = new JOptionPane();
								notice.showMessageDialog(null, "ÇÕ°ÝÀÔ´Ï´Ù.");
							} else if (test == 1) { // Ã¤Á¡ °á°ú°¡ ºÒÇÕ°ÝÀÏ °æ¿ì

								for (i = 0; i < num; i++) {
									this.remove(OneWordpanel[i]);
								}
								num--;

								score.setText("Á¡¼ö");

								for (i = 0; i < num; i++) {
									OneWordpanel[i] = new JPanel();
									OneWordpanel[i].setLayout(new GridLayout(0, 2));
									OneWordpanel[i].add(korWord[i] = new JLabel(v.get(i).getKor()),
											BorderLayout.CENTER);
									OneWordpanel[i].add(engWord[i] = new JTextField());
								}
								for (i = 0; i < num; i++) { // ´Ü¾î°¡ ´Ù ³ª¿Ã ¼ö ÀÖµµ·Ï Áßº¹ Á¦°Å
									a[i] = random.nextInt(num);
									for (j = 0; j < i; j++) {
										if (a[i] == a[j]) {
											i--;
										}
									}
								}
								for (i = 0; i < num; i++) {
									this.add(OneWordpanel[a[i]]);
									// OneWordPanelÀº 30°³ÀÇ ´Ü¾î ¼ø¼­´ë·Î ÀúÀå, Ãâ·Â¸¸ ¹è¿­ ÀÎµ¦½º °ª¿¡ µû¸¥ ÆÐ³Î ·£´ý Ãâ·Â
								}
								test = 0;
								TestNum++;
							}
						} else if (TestNum > 4) { // ±âÈ¸´Â 5¹ø
							JOptionPane notice = new JOptionPane();
							notice.showMessageDialog(null, "¸ðµç ±âÈ¸°¡ ³¡³µ½À´Ï´Ù." + "´Ù½Ã °øºÎÇÏ½Ê½Ã¿À.");
							dispose();
							new mainFrame();
						}
					}
				}

			} // wordPanel class

			WordPanel WordPanel = new WordPanel();
			WordPanel.setLayout(new GridLayout(0, 1));

			JPanel systemPanel = new JPanel();

			JPanel buttonPanel = new JPanel();
			buttonPanel.setSize(0, 400);
			buttonPanel.setLayout(new GridLayout(3, 1));
			JPanel b_Panel_1 = new JPanel();
			b_Panel_1.add(B_1);
			B_1.addActionListener(WordPanel);
			JPanel b_Panel_2 = new JPanel();
			b_Panel_2.add(B_2);
			B_2.addActionListener(this);
			JPanel b_Panel_3 = new JPanel();
			b_Panel_3.add(B_3);
			B_3.addActionListener(WordPanel);

			buttonPanel.add(b_Panel_1, BorderLayout.CENTER);
			buttonPanel.add(b_Panel_2, BorderLayout.CENTER);
			buttonPanel.add(b_Panel_3, BorderLayout.CENTER);
			systemPanel.add(buttonPanel, BorderLayout.NORTH);

			JPanel memoPanel = new JPanel();
			JLabel memo = new JLabel("¸Þ¸ðÀå");
			memoPanel.add(memo);
			memozang.setLineWrap(true); // ¶óÀÎ ÀÚµ¿ ³Ñ±â±â
			JScrollPane scrollPanel = new JScrollPane(memozang, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // ½ºÅ©·Ñ

			memoPanel.add(scrollPanel);
			systemPanel.add(memoPanel);

			JPanel SaveMemoPanel = new JPanel();
			SaveMemoPanel.add(SaveMemo);
			SaveMemo.addActionListener(this);
			systemPanel.add(SaveMemoPanel);

			JPanel scorePanel = new JPanel();
			score = new JLabel("Á¡¼ö");
			score.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 85));
			scorePanel.add(score);
			systemPanel.add(scorePanel, BorderLayout.SOUTH);

			JPanel conclusionPanel = new JPanel();
			backButton.addActionListener(this);
			exitProgram.addActionListener(this);
			conclusionPanel.add(backButton);
			conclusionPanel.add(exitProgram);
			systemPanel.add(conclusionPanel, BorderLayout.SOUTH);

			add(WordPanel);
			add(systemPanel);

			this.setResizable(false); // JFrame Å©±â °íÁ¤
			this.setLocationRelativeTo(null); // JFrame À§Ä¡ °¡¿îµ¥ °íÁ¤

			setVisible(true);
		}

		wrongNote wrongNote;

		class wrongNote extends JDialog {
			JPanel[] WrongNotePanel = new JPanel[num];

			JLabel KorWordLabel = new JLabel();
			JLabel EngWordLabel = new JLabel();

			public wrongNote(String string) {
				JPanel note = new JPanel();

				for (int i = 0; i < num; i++) {
					WrongNotePanel[i] = new JPanel();
					WrongNotePanel[i].setLayout(new GridLayout(0, 3));
					WrongNotePanel[i].add(KorWordLabel = new JLabel(v.get(i).getKor())); // ¶æ
					WrongNotePanel[i].add(EngWordLabel = new JLabel(v.get(i).getEng())); // ¿µ´Ü¾î
					if (data[i].equalsIgnoreCase(v.get(i).getEng()) == true) { // ³» ´ä¾ÈÀÌ Á¤´äÀÏ½Ã
						JLabel MyWordLabel = new JLabel(data[i]); // ³» ´ä¾ÈÀ» ÀûÀº ¶óº§
						MyWordLabel.setForeground(Color.BLUE); // Á¤´äÀº ÆÄ¶û
						WrongNotePanel[i].add(MyWordLabel);
					} else if (data[i].equalsIgnoreCase(v.get(i).getEng()) == false) { // ³» ´ä¾ÈÀÌ ¿À´äÀÏ½Ã
						JLabel MyWordLabel = new JLabel(data[i]);
						MyWordLabel.setForeground(Color.RED); // ¿À´äÀº »¡°­
						WrongNotePanel[i].add(MyWordLabel);
					}
					note.add(WrongNotePanel[i]);
				}
				note.setLayout(new GridLayout(0, 1));
				this.add(note);
				this.setSize(400, 800);
				this.setLocationRelativeTo(null); // Ã¢ À©µµ¿ì °¡¿îµ¥
				this.setTitle("¿À´ä³ëÆ®");
				this.setModal(false); // ¿À´ä³ëÆ®¸¦ ÄÑµµ ¸ÞÀÎ Ã¢ ½ÇÇà °¡´É (ºÎ¸ð ÇÁ·¹ÀÓ »ç¿ë °¡´É)
				this.setVisible(true);
			}
		}

		class MemoAddFrame extends JDialog implements ActionListener {
			JPanel MemoNamePanel = new JPanel();
			JLabel MemoName = new JLabel("ÀúÀåÇÒ ¸Þ¸ðÀå ÀÌ¸§ : ");
			JTextField MemoNameWrite = new JTextField(10);
			JPanel MemoAddPanel = new JPanel();
			JButton MemoAdd = new JButton("ÀúÀå");

			public MemoAddFrame() {
				this.setTitle("¸Þ¸ðÀå ÀúÀåÇÏ±â");
				MemoNamePanel.add(MemoName);
				MemoNamePanel.add(MemoNameWrite);
				MemoAddPanel.add(MemoAdd);
				MemoAdd.addActionListener(this);
				MemoNamePanel.add(MemoAddPanel);

				getContentPane().add(MemoNamePanel);

				this.setSize(200, 150);
				this.setModal(true);
				this.setVisible(true);

			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == MemoAdd) {
					String MemoNameString = MemoNameWrite.getText();
					FileWriter memoWriter = null;

					try {
						File Memofile = new File(
								"C:\\Users\\a\\Desktop\\ÀÚ¹Ù2\\¿µ´Ü¾î ÇÁ·Î±×·¥\\¿µ´Ü¾î °øºÎ ¸Þ¸ðÀå\\" + MemoNameString + ".txt");
						try {
							memoWriter = new FileWriter(
									"C:\\Users\\a\\Desktop\\ÀÚ¹Ù2\\¿µ´Ü¾î ÇÁ·Î±×·¥\\¿µ´Ü¾î °øºÎ ¸Þ¸ðÀå\\" + MemoNameString + ".txt",
									true);
							PrintWriter printWriter = new PrintWriter(memoWriter);
							printWriter.print(memozang.getText());

							JOptionPane notice = new JOptionPane();
							notice.showMessageDialog(null, "¸Þ¸ðÀå ÀúÀå ¿Ï·á: " + MemoNameString + ".txt");

							printWriter.close();

						} catch (Exception e1) {
							e1.getStackTrace();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					dispose();
				}
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == B_2) { // ¿À´ä³ëÆ®¸¦ ´­·¶Áö¸¸
				if (test == 0) { // Ã¤Á¡ÀÌ ¾È µÈ °æ¿ì
					JOptionPane notice = new JOptionPane();
					notice.showMessageDialog(null, "Ã¤Á¡À» ¸ÕÀú ÁøÇàÇÏ½Ê½Ã¿À.");
				} else {
					wrongNote = new wrongNote(e.getActionCommand());
				}
			} else if (e.getSource() == B_3) { // Àç½ÃÇèÀ» ´­·¶Áö¸¸
				if (test == 0) { // Ã¤Á¡ÀÌ ¾È µÈ °æ¿ì
					JOptionPane notice = new JOptionPane();
					notice.showMessageDialog(null, "Ã¤Á¡À» ¸ÕÀú ÁøÇàÇÏ½Ê½Ã¿À.");
				} else if (test == 2) { // Ã¤Á¡ °á°ú°¡ ÇÕ°ÝÀÏ °æ¿ì
					JOptionPane notice = new JOptionPane();
					notice.showMessageDialog(null, "ÇÕ°ÝÀÔ´Ï´Ù.");
				}
			} else if (e.getSource() == SaveMemo) {
				if ((memozang.getText()).equals(null)) {
					JOptionPane notice = new JOptionPane();
					notice.showMessageDialog(null, "¸Þ¸ðÀå¿¡ ÀúÀåÇÒ ³»¿ëÀ» ÀÔ·ÂÇÏ½Ã¿À");
				} else {
					new MemoAddFrame();
				}
			} else if (e.getSource() == backButton) {
				this.dispose();
				new mainFrame();
			} else if (e.getSource() == exitProgram) {
				JOptionPane notice = new JOptionPane();
				notice.showMessageDialog(null, "°í»ýÇÏ¼Ì½À´Ï´Ù");
				this.dispose();
			}
		}
	}

	int studyTime = 1;

	class studyNote extends JDialog { // ´Ü¾î°¡ ¸¹¾ÆÁö´Ï ½ºÅ©·ÑÀ» »ç¿ëÇÒ °Í

		JLabel KorWordStudyLabel;
		JLabel EngWordStudyLabel;

		public studyNote() throws IOException {

			if (studyTime == 1) {
				new wordList();
			}

			JPanel studyNoteWholePanel = new JPanel();
			JPanel[] studyNotePanel = new JPanel[(v.size()) + AddWordNum];
			System.out.println(v.size());
			System.out.println(studyTime);
			System.out.println(AddWordNum);

			studyNoteWholePanel.setLayout(new GridLayout(0, 1));

			File fileForStudy = new File("C:\\Users\\a\\Desktop\\ÀÚ¹Ù2\\¿µ´Ü¾î ÇÁ·Î±×·¥\\¿µ´Ü¾î ÇÁ·Î±×·¥ ´Ü¾îÀå\\" + fileNameString);
			ArrayList<String> EngStr = new ArrayList<>();
			ArrayList<String> KorStr = new ArrayList<>();

			try (BufferedReader studyBr = new BufferedReader(new FileReader(fileForStudy))) {

				String line;

				while ((line = studyBr.readLine()) != null) {
					String[] splitStr = line.split("\\s+");
					EngStr.add(splitStr[0]);
					KorStr.add(splitStr[1]);
				}
			}

			for (int i = 0; i < (v.size() + AddWordNum); i++) {
				studyNotePanel[i] = new JPanel();
				studyNotePanel[i].setLayout(new GridLayout(0, 2));
				studyNotePanel[i].add(KorWordStudyLabel = new JLabel(KorStr.get(i))); // ¶æ
				studyNotePanel[i].add(EngWordStudyLabel = new JLabel(EngStr.get(i))); // ¿µ´Ü¾î
				EngWordStudyLabel.setForeground(Color.BLUE);
				studyNoteWholePanel.add(studyNotePanel[i]);
			}

			this.add(studyNoteWholePanel);
			this.setSize(400, 800);
			this.setLocationRelativeTo(null); // Ã¢ À©µµ¿ì °¡¿îµ¥
			this.setTitle("°øºÎ³ëÆ®");
			this.setModal(false); // °øºÎ³ëÆ®¸¦ ÄÑµµ ¸ÞÀÎ Ã¢ ½ÇÇà °¡´É (ºÎ¸ð ÇÁ·¹ÀÓ »ç¿ë °¡´É)
			this.setVisible(true);
		}
	}

	public mainFrame() {
		setTitle("¿µ´Ü¾î ¾Ï±â ÇÁ·Î±×·¥");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);

		class myPanel extends JPanel implements ActionListener {

			public myPanel() {
				setLayout(new GridLayout(2, 0));
				add(title);
				title.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
				startPanel.setLayout(new GridLayout(4, 0));
				fileaddPanel.add(fileaddButton);
				fileaddButton.addActionListener(this);
				fileaddPanel.add(wordaddButton);
				wordaddButton.addActionListener(this);
				startPanel.add(fileaddPanel, BorderLayout.CENTER);
				fileSelectPanel.add(select);
				startPanel.add(fileSelectPanel, BorderLayout.CENTER);
				select.addActionListener(this);
				fileInformation.setOpaque(true);
				fileInformation.setBackground(Color.WHITE);
				startPanel.add(fileInformation);
				testStartPanel.add(studyStart);
				testStartPanel.add(testStart);
				startPanel.add(testStartPanel, BorderLayout.CENTER);
				studyStart.addActionListener(this);
				testStart.addActionListener(this);
				add(startPanel);

			}

			class fileAddFrame extends JDialog implements ActionListener {
				JPanel fileNamePanel = new JPanel();
				JLabel fileName = new JLabel("»ý¼ºÇÒ ÆÄÀÏ ÀÌ¸§ : ");
				JTextField fileNameWrite = new JTextField(10);
				JPanel fileAddPanel = new JPanel();
				JButton fileAdd = new JButton("»ý¼º");

				public fileAddFrame() {
					this.setTitle("¿µ´Ü¾î ÆÄÀÏ Ãß°¡ÇÏ±â");
					fileNamePanel.add(fileName);
					fileNamePanel.add(fileNameWrite);
					fileAddPanel.add(fileAdd);
					fileAdd.addActionListener(this);
					fileNamePanel.add(fileAddPanel);

					getContentPane().add(fileNamePanel);

					this.setSize(200, 150);
					this.setModal(true);
					this.setVisible(true);

				}

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == fileAdd) {
						fileNameString = fileNameWrite.getText();
						// ÆÄÀÏ »ý¼º
						try {
							// ÆÄÀÏ °´Ã¼ »ý¼º
							File file = new File(
									"C:\\Users\\a\\Desktop\\ÀÚ¹Ù2\\¿µ´Ü¾î ÇÁ·Î±×·¥\\¿µ´Ü¾î ÇÁ·Î±×·¥ ´Ü¾îÀå\\" + fileNameString + ".txt");
							if (file.createNewFile()) {
								JOptionPane notice = new JOptionPane();
								notice.showMessageDialog(null, "ÆÄÀÏ »ý¼º ¿Ï·á: " + file.getName());
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						dispose();
					}
				}
			}

			class wordAddFrame extends JDialog implements ActionListener {

				JLabel fileInfor = new JLabel("null");

				JPanel WordAddButtonPanel = new JPanel();
				JButton WordAdd = new JButton("Ãß°¡");

				JPanel WordAddSystemPanel = new JPanel();

				JPanel EngAddPanel = new JPanel();
				JLabel EngAddLabel = new JLabel("¿µ´Ü¾î : ");
				JTextField EngAddField = new JTextField(15);

				JPanel KorAddPanel = new JPanel();
				JLabel KorAddLabel = new JLabel("¶æ : ");
				JTextField KorAddField = new JTextField(15);

				JPanel WordAddWholePanel = new JPanel();

				public wordAddFrame() {
					this.setTitle("¿µ´Ü¾î Ãß°¡ÇÏ±â");
					WordAddWholePanel.setLayout(new GridLayout(3, 0));
					WordAddWholePanel.add(fileInfor);
					if (selecting == 0) {
						JOptionPane notice = new JOptionPane();
						notice.showMessageDialog(null, "ÆÄÀÏÀ» ¼±ÅÃÇÏ½Ê½Ã¿À.");
					} else if (selecting == 1) {
						fileInfor.setText(fileInformation.getText());
						WordAddSystemPanel.setLayout(new GridLayout(2, 0));

						EngAddPanel.add(EngAddLabel);
						EngAddPanel.add(EngAddField);
						WordAddSystemPanel.add(EngAddPanel);

						KorAddPanel.add(KorAddLabel);
						KorAddPanel.add(KorAddField);
						WordAddSystemPanel.add(KorAddPanel);

						WordAddWholePanel.add(WordAddSystemPanel);
						WordAddButtonPanel.add(WordAdd);
						WordAdd.addActionListener(this);
						WordAddWholePanel.add(WordAddButtonPanel);

						getContentPane().add(WordAddWholePanel);

						this.setSize(400, 200);
						this.setModal(true);
						this.setVisible(true);
					}
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					// ´Ü¾î Ãß°¡
					if (e.getSource() == WordAdd) {
						// ÆÄÀÏ À§Ä¡ : fileInformation
						// ¿µ¾î : EngAddField , ¶æ : KorAddField
						FileWriter fileWriter = null;

						if (Pattern.matches("^[¤¡-¤¾°¡-ÆR]*$", KorAddField.getText()) != true
								|| Pattern.matches("^[a-zA-Z]*$", EngAddField.getText()) != true) {
							if (Pattern.matches("^[¤¡-¤¾°¡-ÆR]*$", KorAddField.getText()) == false
									&& Pattern.matches("^[a-zA-Z]*$", EngAddField.getText()) == false) {
								JOptionPane notice = new JOptionPane();
								notice.showMessageDialog(null, "¿µ´Ü¾î¸¦ ÀÔ·ÂÇÒ½Ã ¿µ¾î¸¸, " + "\n" + "¶æÀ» ÀÔ·ÂÇÒ½Ã ÇÑ±Û¸¸ ÀÔ·ÂÇÏ½Ã¿À.");
							} else if (Pattern.matches("^[¤¡-¤¾°¡-ÆR]*$", KorAddField.getText()) == false) {
								JOptionPane notice = new JOptionPane();
								notice.showMessageDialog(null, "¶æÀ» ÀÔ·ÂÇÒ½Ã ÇÑ±Û¸¸ ÀÔ·ÂÇÏ½Ã¿À.");
							} else if (Pattern.matches("^[a-zA-Z]*$", EngAddField.getText()) == false) {
								JOptionPane notice = new JOptionPane();
								notice.showMessageDialog(null, "¿µ´Ü¾î¸¦ ÀÔ·ÂÇÒ½Ã ¿µ¾î¸¸ ÀÔ·ÂÇÏ½Ã¿À.");
							}
						} else {
							try {
								fileWriter = new FileWriter(
										"C:\\Users\\a\\Desktop\\ÀÚ¹Ù2\\¿µ´Ü¾î ÇÁ·Î±×·¥\\¿µ´Ü¾î ÇÁ·Î±×·¥ ´Ü¾îÀå\\" + fileNameString, true);
								PrintWriter printWriter = new PrintWriter(fileWriter);
								printWriter.print(EngAddField.getText() + " ");
								printWriter.print(KorAddField.getText());
								printWriter.print("\n");
								JOptionPane notice = new JOptionPane();
								notice.showMessageDialog(null, EngAddField.getText() + "°¡ Ãß°¡µÇ¾ú½À´Ï´Ù.");
								AddWordNum++;
								EngAddField.setText(null);
								KorAddField.setText(null);
								printWriter.flush();

							} catch (Exception e1) {
								e1.getStackTrace();
							}
						}
					}

				}
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == fileaddButton) {
					new fileAddFrame();

				} else if (e.getSource() == select) {
					int returnVal = fc.showOpenDialog(this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						try {
							fileInformation.setText("¼±ÅÃµÈ ÆÄÀÏ : " + file.getName());
							fileNameString = file.getName();
							selecting = 1;
						} catch (Exception e1) {

						}
					}
				} else if (e.getSource() == wordaddButton) {
					new wordAddFrame();
				} else if (e.getSource() == studyStart) {
					if (selecting == 0) {
						JOptionPane notice = new JOptionPane();
						notice.showMessageDialog(null, "ÆÄÀÏÀ» ¼±ÅÃÇÏ½Ê½Ã¿À.");
					} else if (selecting == 1) {
						try {
							new studyNote();
							studyTime++;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}

				else if (e.getSource() == testStart) {
					if (selecting == 0) {
						JOptionPane notice = new JOptionPane();
						notice.showMessageDialog(null, "ÆÄÀÏÀ» ¼±ÅÃÇÏ½Ê½Ã¿À.");
					} else if (selecting == 1) {
						dispose(); // Ã¢ ¾ø¾Ö±â
						new finalTest();
					}
				}
			}
		}

		JPanel myPanel = new myPanel();

		this.add(myPanel);
		this.setResizable(false); // JFrame Å©±â °íÁ¤
		setVisible(true);
	}

	public static void main(String[] args) {
		new mainFrame();
	}
}
