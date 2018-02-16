package com.insight.codingchallenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CampaignContributionAnalyser {

	// map to store recipientId and list of repeat donors to the recipient
	private static Map<String, List<Donor>> recipient_repeat_donor_map = new HashMap<>();

	// map to store donors and their contribution data(year,amount)
	private static Map<Donor, List<DonorContribution>> donors = new HashMap<>();

	private static final String OUTPUT_FILE = "repeat_donors.txt";
	private static final String PERCENTILE_FILE = "percentile.txt";

	private static PrintWriter printWriter;

	private static int percentile;

	public static void main(String[] args) throws IOException {

		CampaignContributionAnalyser analyser = new CampaignContributionAnalyser();
		analyser.analyze();

	}

	private void analyze() {
		String data;
		BufferedReader input_reader = null;
		BufferedReader percentile_reader = null;
		FileWriter fileWriter;

		try {

			percentile_reader = new BufferedReader(new FileReader("input//" + PERCENTILE_FILE));
			while ((data = percentile_reader.readLine()) != null) {
				percentile = Integer.parseInt(data);
			}

			fileWriter = new FileWriter("output//" + OUTPUT_FILE);
			printWriter = new PrintWriter(fileWriter);
		
			input_reader = new BufferedReader(new FileReader("input//itcont.txt"));
			while ((data = input_reader.readLine()) != null) {
				processData(data);
			}

		
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				percentile_reader.close();
				input_reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void processData(String data) {

		String[] fields = data.split("\\|");

		String name = fields[7] != null ? fields[7].trim() : "";
		String zip = fields[10] != null ? fields[10].trim() : "";
		String recipient = fields[0] != null ? fields[0].trim() : "";
		String transaction_date = fields[13] != null ? fields[13].trim() : "";
		String transaction_amount = fields[4] != null ? fields[14].trim() : "";
		String other_id = fields[15] != null ? fields[15].trim() : "";

		if (!validData(name, zip, recipient, transaction_date, transaction_amount, other_id)) {
			return;
		}

		// get 5 digit zipcode
		String zipcode = zip.substring(0, 5);

		// get the donation year
		int donation_year = Integer.parseInt(transaction_date.substring(4));

		// get the donation amount in a float variable
		float donation_amount = Float.parseFloat(transaction_amount);

		// create a donor and donor contribution object
		Donor donor = new Donor(name, zipcode);
		DonorContribution contribution = new DonorContribution(donation_year, donation_amount);

		// add donor and donor contribution to donors map
		addToDonorsMap(donor, contribution);

		// check if the donor is a repeat donor.
		boolean repeat_donor = isRepeatDonor(donor, donation_year);

		// if repeat donor, add the donors to recipient_repeat_donor_map. write
		// the data to file
		if (repeat_donor) {
			addToRecipientRepeatDonorMap(recipient, donor);
			writeToFile(donation_year, zipcode, recipient);
		}

	}

	private void addToDonorsMap(Donor donor, DonorContribution contribution) {

		List<DonorContribution> contributions = donors.get(donor);
		if (contributions == null)
			contributions = new ArrayList<>();
		contributions.add(contribution);
		donors.put(donor, contributions);
	}

	private void addToRecipientRepeatDonorMap(String recipient, Donor donor) {

		List<Donor> repeat_donors = recipient_repeat_donor_map.get(recipient);
		if (repeat_donors == null)
			repeat_donors = new ArrayList<>();
		repeat_donors.add(donor);
		recipient_repeat_donor_map.put(recipient, repeat_donors);

	}

	// check if the donor made any contributions in the years prior to
	// donation_year. if any contribution made, repeatDonor=true
	private boolean isRepeatDonor(Donor donor, int donation_year) {

		if (donors.containsKey(donor)) {
			List<DonorContribution> contributions = donors.get(donor);
			for (DonorContribution contribution : contributions) {
				if (contribution.getYear() < donation_year)
					return true;
			}
		}
		return false;
	}

	// method to write data to the output file repeat_donors.txt
	private void writeToFile(int year, String zipcode, String recipient) {

		List<Donor> repeated_donors = recipient_repeat_donor_map.get(recipient);
		int donor_count = 0;
		float sum = 0;
		// use this for percentile calculation
		List<Float> amounts = new ArrayList<>();
		for (Donor donor : repeated_donors) {
			if (donor.getZipcode().equals(zipcode)) {
				++donor_count;
				List<DonorContribution> contributions = donors.get(donor);
				for (DonorContribution contribution : contributions)
					if (contribution.getYear() == year) {
						sum = sum + contribution.getAmount();
						amounts.add(contribution.getAmount());

					}
			}

		}
		int percentile_value = UtilClass.getPercentileValue(amounts, percentile);
		printWriter.println(
				recipient + "|" + zipcode + "|" + year + "|" + percentile_value + "|" + Math.round(sum) + "|" + (donor_count));
		printWriter.flush();

	}

	// method to validate the data for any discrepencies
	private static boolean validData(String name, String zipcode, String recipient, String transaction_date,
			String transaction_amount, String other_id) {

		if (!UtilClass.isEmpty(other_id)) {
			// System.out.println("non-empty other id");
			return false;
		}
		if (UtilClass.isEmpty(recipient)) {
			// System.out.println("invalid recipient");
			return false;
		}
		if (!UtilClass.isValidZip(zipcode)) {
			// System.out.println("invalid zipcode"+zipcode);
			return false;
		}
		if (!UtilClass.isValidName(name)) {
			// System.out.println("invalid name");
			return false;
		}
		if (!UtilClass.isValidDate(transaction_date)) {
			// System.out.println("invalid transaction date");
			return false;
		}
		if (!UtilClass.isValidAmount(transaction_amount)) {
			// System.out.println("invalid transaction amount");
			return false;
		}
		return true;

	}

}
