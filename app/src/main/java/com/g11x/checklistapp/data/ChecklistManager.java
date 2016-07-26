/*
 * Copyright © 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.g11x.checklistapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChecklistManager {
  private static Checklist checklist;

  public static Checklist get(Context context) {
    if (checklist== null) {
      SharedPreferences preferences = context.getSharedPreferences("checklist", Context.MODE_PRIVATE);
      String items = preferences.getString("doneness", null);

      if (items == null) {
        checklist = fromDefault();
      } else {
        ArrayList<Boolean> doneness = new ArrayList<>();

        for (String item : items.split(",")) {
          doneness.add(Boolean.valueOf(item));
        }

        checklist = fromDoneness(doneness);
      }
    }

    return checklist;
  }

  public static void save(Context context, Checklist checklist) {
    StringBuilder doneness = new StringBuilder();
    for (ChecklistItem item : checklist.getItems()) {
      doneness.append(String.valueOf(item.isDone())).append(",");
    }
    SharedPreferences settings = context.getSharedPreferences("checklist", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString("doneness", doneness.toString());
    editor.apply();
  }

  private static Checklist fromDefault() {
    return fromDoneness(Arrays.asList(false, false, false, false, false));
  }

  private static Checklist fromDoneness(List<Boolean> doneness) {
    return Checklist.of(Arrays.asList(
        ChecklistItem.of("Get social security card",
            "In the United States, a Social Security number is a number assigned to U.S. Citizens, permanent residents, and temporary (working) residents. The primary purpose is to track individuals for taxes and identification. A Social Security lists your name and Social Security number, and is often necessary for obtaining other items, such as a state identification card. People don't usually carry their Social Security cards with them everywhere, since they are not usually necessary on a day-to-day basis.",
            doneness.get(0),
            "US Social Security Administration, 1029 Camino La Costa, Austin, TX 78752",
            Uri.parse("https://www.google.com/maps/place/US+Social+Security+Administration/@30.3243715,-97.7028468,17z/data=!3m1!4b1!4m5!3m4!1s0x8644ca20295a7e57:0x849908e001c1d623!8m2!3d30.3243715!4d-97.7006581"),
            null,
            "1-800-772-1213"),
        ChecklistItem.of("Get state identification card",
            "State identification cards, or state ID cards, are used to identify people for everyday tasks such as financial transactions and age verification. In order to obtain a state ID card in Texas, you will need a Social Security card. You should carry your state ID card with you every day.",
            doneness.get(1),
            "Texas Department of Public Safety 5805 N Lamar Blvd, Austin, TX 78752",
            Uri.parse("https://www.google.com/maps/place/Texas+Department+of+Public+Safety/@30.3264658,-97.7255281,17z/data=!3m1!4b1!4m5!3m4!1s0x8644ca46dcf0be55:0x1a342c9d9863d05b!8m2!3d30.3264658!4d-97.7233394"),
            null,
            "1-512-424-2000"),
        ChecklistItem.of("Submit information to unemployment office",
            "In the United States, if you are looking for employment, but do not currently have a job, you can often collect 'unemployment benefits' for a short period of time. These payments are provided by the state government, and they help you pay for basic needs until you have a job to support yourself.",
            doneness.get(2),
            "Texas Workforce Commission 2810 E Martin Luther King Jr Blvd, Austin, TX 78702",
            Uri.parse("https://www.google.com/maps/place/Texas+Workforce+Commission/@30.2819065,-97.7135026,17z/data=!3m1!4b1!4m5!3m4!1s0x8644b5e91d5120d7:0x2fe0ab2c19a88990!8m2!3d30.2819065!4d-97.7113139"),
            "laborinfo@twc.state.tx.us", "1-512-480-8101"),
        ChecklistItem.of("Buy a bus pass",
            "The Capital Metro system provides public transportation via bus and sometimes light rail anywhere within Austin city limits. When you board a bus or train, you will be required to either pay for the ride, or show a bus pass, so you should carry a bus pass with you at all times.",
            doneness.get(3),
            "Capital Metro 209 West 9th Street, Austin, TX 78701",
            Uri.parse("https://www.google.com/maps/place/Capital+Metro+Transit+Store/@30.271223,-97.7462346,17z/data=!3m1!4b1!4m5!3m4!1s0x8644b508420e242d:0x8df332980f4b004b!8m2!3d30.271223!4d-97.7440459"),
            "customer.service@capmetro.org", " 1-512-389-7454"),
        ChecklistItem.of("Enroll children in school",
            "In the United States, public school is provided for children from 5 to 18 years of age. In Austin, the school year runs from September until June. In order to attend public school, children must be registered at the Austin Independent School District office in downtown Austin.",
            doneness.get(4),
            "Austin Independent School District 1111 W 6th Street Austin, TX 78703",
            Uri.parse("https://www.google.com/maps/place/Austin+Independent+School+District/@30.271564,-97.7587266,17z/data=!3m1!4b1!4m5!3m4!1s0x8644b513a2be5731:0xb4f1cb61acd29991!8m2!3d30.271564!4d-97.7565379"),
            null,
            "1-512-414-1700")));
  }
}
