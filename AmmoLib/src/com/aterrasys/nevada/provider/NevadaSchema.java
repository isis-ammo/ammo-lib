// THIS IS GENERATED CODE, YOU SHOULD COPY THIS FOR YOUR HAND EDITS
package com.aterrasys.nevada.provider;

public class NevadaSchema extends NevadaSchemaBase {

   public static final int DATABASE_VERSION = 1;
   
   public static final String RTC_LIST_PEOPLE_TYPE = "urn:aterrasys.com:/api/rtc/people/list/";
   public static final String RTC_LIST_CHANNEL_TYPE = "urn:aterrasys.com:/api/rtc/channel/list/";
   public static final String BFT_LOCATE_PERSON_TYPE = "urn:aterrasys.com:/api/bft/people/locate/";

      public static class UserpeopleTableSchema extends UserpeopleTableSchemaBase {

         protected UserpeopleTableSchema() { super(); }
      /**
        Add relation constants as appropriate.
        i.e.
        public static final String <NAME> = "<sponsor>.provider.<name>.<table>.action.<NAME>";
        e.g.
        public static final String CONSTANT = "com.aterrasys.nevada.provider.nevada.userpeople.action.CONSTANT";

        public static final String PRIORITY_SORT_ORDER = 
               UserpeopleTableSchemaBase.EXPIRATION + " DESC, " +
               UserpeopleTableSchemaBase.MODIFIED_DATE + " DESC ";
      */
      }

      public static class ChannelsTableSchema extends ChannelsTableSchemaBase {

         protected ChannelsTableSchema() { super(); }
      /**
        Add relation constants as appropriate.
        i.e.
        public static final String <NAME> = "<sponsor>.provider.<name>.<table>.action.<NAME>";
        e.g.
        public static final String CONSTANT = "com.aterrasys.nevada.provider.nevada.channels.action.CONSTANT";

        public static final String PRIORITY_SORT_ORDER = 
               ChannelsTableSchemaBase.EXPIRATION + " DESC, " +
               ChannelsTableSchemaBase.MODIFIED_DATE + " DESC ";
      */
      }

      public static class UnitsTableSchema extends UnitsTableSchemaBase {

         protected UnitsTableSchema() { super(); }
      /**
        Add relation constants as appropriate.
        i.e.
        public static final String <NAME> = "<sponsor>.provider.<name>.<table>.action.<NAME>";
        e.g.
        public static final String CONSTANT = "com.aterrasys.nevada.provider.nevada.unit.action.CONSTANT";

        public static final String PRIORITY_SORT_ORDER = 
               UnitTableSchemaBase.EXPIRATION + " DESC, " +
               UnitTableSchemaBase.MODIFIED_DATE + " DESC ";
      */
      }

      public static class UnitpersonTableSchema extends MembersTableSchemaBase {

         protected UnitpersonTableSchema() { super(); }
      /**
        Add relation constants as appropriate.
        i.e.
        public static final String <NAME> = "<sponsor>.provider.<name>.<table>.action.<NAME>";
        e.g.
        public static final String CONSTANT = "com.aterrasys.nevada.provider.nevada.unitperson.action.CONSTANT";

        public static final String PRIORITY_SORT_ORDER = 
               UnitpersonTableSchemaBase.EXPIRATION + " DESC, " +
               UnitpersonTableSchemaBase.MODIFIED_DATE + " DESC ";
      */
      }

      public static class LocationtrackingTableSchema extends LocationsTableSchemaBase {

         protected LocationtrackingTableSchema() { super(); }
      /**
        Add relation constants as appropriate.
        i.e.
        public static final String <NAME> = "<sponsor>.provider.<name>.<table>.action.<NAME>";
        e.g.
        public static final String CONSTANT = "com.aterrasys.nevada.provider.nevada.locationtracking.action.CONSTANT";

        public static final String PRIORITY_SORT_ORDER = 
               LocationtrackingTableSchemaBase.EXPIRATION + " DESC, " +
               LocationtrackingTableSchemaBase.MODIFIED_DATE + " DESC ";
      */
      }

      public static class MapannotationTableSchema extends MapannotationTableSchemaBase {

         protected MapannotationTableSchema() { super(); }
      /**
        Add relation constants as appropriate.
        i.e.
        public static final String <NAME> = "<sponsor>.provider.<name>.<table>.action.<NAME>";
        e.g.
        public static final String CONSTANT = "com.aterrasys.nevada.provider.nevada.mapannotation.action.CONSTANT";

        public static final String PRIORITY_SORT_ORDER = 
               MapannotationTableSchemaBase.EXPIRATION + " DESC, " +
               MapannotationTableSchemaBase.MODIFIED_DATE + " DESC ";
      */
      }

      
}