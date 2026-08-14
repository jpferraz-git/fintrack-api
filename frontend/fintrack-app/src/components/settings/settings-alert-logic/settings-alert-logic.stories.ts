import type { Meta, StoryObj } from '@storybook/angular';
import { SettingsAlertLogic } from './settings-alert-logic.component';

const meta: Meta<SettingsAlertLogic> = {
  title: 'Components/SettingsAlertLogic',
  component: SettingsAlertLogic,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<SettingsAlertLogic>;

export const Primary: Story = {
  args: {},
};
